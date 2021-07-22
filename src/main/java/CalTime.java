import com.nan.mapper.DailyMapper;
import com.nan.mapper.PowerCTLMapper;
import com.nan.mapper.PowerCTLMapperImpl;
import com.nan.pojo.Daily;
import com.nan.pojo.PowerCTL;
import com.nan.pojo.TimeInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import com.nan.pojo.Record;

public class CalTime {

    static public void updatePowerCTL(String ip){  //更新PowerControl表
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        PowerCTLMapper powerCTLMapper=context.getBean("powerCTLMapper",PowerCTLMapper.class);
        int res= powerCTLMapper.HasInfo(ip);
        String tOntime=""+calTOnTime(getServTime(ip)).intValue();
        String tOfftime=""+calTOffTime(getServTime(ip)).intValue();
        if(res==0){ //无服务器开关机时间信息，则插入，否则更新
            powerCTLMapper.insertInfo(new PowerCTL(ip,tOntime,tOfftime));
        }
        else {
            powerCTLMapper.updateInfo(new PowerCTL(ip,tOntime,tOfftime));
        }
    }

    static public Centroid getServTime(String ip){  //调用算法，计算服务器开关机时间
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        DailyMapper dailyMapper=context.getBean("dailyMapper",DailyMapper.class);
        List<Daily> dailyList=dailyMapper.getServDaily(ip);
        List<Record> records=new ArrayList<>();
        for(Daily daily:dailyList){
            Map<String,Double> timeMap=new HashMap<>();
            timeMap.put("tOn",Double.parseDouble(daily.gettOnFirst()));
            timeMap.put("tOff",Double.parseDouble(daily.gettOffLast()));
            Record record=new Record(daily.getDdate(),timeMap);
            records.add(record);
        }
        Distance distance=new Distance() {
            @Override
            public double calculate(Map<String, Double> f1, Map<String, Double> f2) {
                double sum = 0;
                for (String key : f1.keySet()) {
                    Double v1 = f1.get(key);
                    Double v2 = f2.get(key);

                    if (v1 != null && v2 != null) {
                        sum += Math.pow(v1 - v2, 2);
                    }
                }

                return Math.sqrt(sum);
            }
        };
        Map<Centroid,List<Record>> map=KMeans.fit(records,1,distance,100);
        Centroid centroid=null;
        for(Centroid key:map.keySet()){
            centroid=key;
        }
        return centroid;
    }
    //返回开关机时间
    static public Double calTOnTime(Centroid centroid){
        return centroid.getCoordinates().get("tOn");
    }

    static public Double calTOffTime(Centroid centroid){
        return centroid.getCoordinates().get("tOff");
    }
}


interface Distance {
    double calculate(Map<String, Double> f1, Map<String, Double> f2);
}

class EuclideanDistance implements Distance {

    @Override
    public double calculate(Map<String, Double> f1, Map<String, Double> f2) {
        double sum = 0;
        for (String key : f1.keySet()) {
            Double v1 = f1.get(key);
            Double v2 = f2.get(key);

            if (v1 != null && v2 != null) {
                sum += Math.pow(v1 - v2, 2);
            }
        }

        return Math.sqrt(sum);
    }
}

class Centroid {

    private Map<String, Double> coordinates;

    public Centroid(Map<String, Double> coordinates) {
        this.coordinates = coordinates;
    }

    public Map<String, Double> getCoordinates() {
        return coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Centroid)) return false;
        Centroid centroid = (Centroid) o;
        return Objects.equals(coordinates, centroid.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates);
    }

    @Override
    public String toString() {
        return "Centroid{" +
                "coordinates=" + coordinates +
                '}';
    }
// constructors, getter, toString, equals and hashcode
}

class KMeans {

    private static final Random random = new Random();

    private static List<Centroid> randomCentroids(List<Record> records, int k) {
        List<Centroid> centroids = new ArrayList<>();
        Map<String, Double> maxs = new HashMap<>();
        Map<String, Double> mins = new HashMap<>();

        for (Record record : records) {
            record.getFeatures().forEach((key, value) -> {
                // compares the value with the current max and choose the bigger value between them
                maxs.compute(key, (k1, max) -> max == null || value > max ? value : max);

                // compare the value with the current min and choose the smaller value between them
                mins.compute(key, (k1, min) -> min == null || value < min ? value : min);
            });
        }

        Set<String> attributes = records.stream()
                .flatMap(e -> e.getFeatures().keySet().stream())
                .collect(toSet());
        for (int i = 0; i < k; i++) {
            Map<String, Double> coordinates = new HashMap<>();
            for (String attribute : attributes) {
                double max = maxs.get(attribute);
                double min = mins.get(attribute);
                coordinates.put(attribute, random.nextDouble() * (max - min) + min);
            }

            centroids.add(new Centroid(coordinates));
        }

        return centroids;
    }
    private static Centroid nearestCentroid(Record record, List<Centroid> centroids, Distance distance) {
        double minimumDistance = Double.MAX_VALUE;
        Centroid nearest = null;

        for (Centroid centroid : centroids) {
            double currentDistance = distance.calculate(record.getFeatures(), centroid.getCoordinates());

            if (currentDistance < minimumDistance) {
                minimumDistance = currentDistance;
                nearest = centroid;
            }
        }

        return nearest;
    }
    private static void assignToCluster(Map<Centroid, List<Record>> clusters,
                                        Record record,
                                        Centroid centroid) {
        clusters.compute(centroid, (key, list) -> {
            if (list == null) {
                list = new ArrayList<>();
            }

            list.add(record);
            return list;
        });
    }
    private static Centroid average(Centroid centroid, List<Record> records) {
        if (records == null || records.isEmpty()) {
            return centroid;
        }

        Map<String, Double> average = centroid.getCoordinates();
        records.stream().flatMap(e -> e.getFeatures().keySet().stream())
                .forEach(k -> average.put(k, 0.0));

        for (Record record : records) {
            record.getFeatures().forEach(
                    (k, v) -> average.compute(k, (k1, currentValue) -> v + currentValue)
            );
        }

        average.forEach((k, v) -> average.put(k, v / records.size()));

        return new Centroid(average);
    }
    private static List<Centroid> relocateCentroids(Map<Centroid, List<Record>> clusters) {
        return clusters.entrySet().stream().map(e -> average(e.getKey(), e.getValue())).collect(toList());
    }
    public static Map<Centroid, List<Record>> fit(List<Record> records,
                                                  int k,
                                                  Distance distance,
                                                  int maxIterations) {
        List<Centroid> centroids = randomCentroids(records, k);
        Map<Centroid, List<Record>> clusters = new HashMap<>();
        Map<Centroid, List<Record>> lastState = new HashMap<>();

        // iterate for a pre-defined number of times
        for (int i = 0; i < maxIterations; i++) {
            boolean isLastIteration = i == maxIterations - 1;

            // in each iteration we should find the nearest centroid for each record
            for (Record record : records) {
                Centroid centroid = nearestCentroid(record, centroids, distance);
                assignToCluster(clusters, record, centroid);
            }

            // if the assignments do not change, then the algorithm terminates
            boolean shouldTerminate = isLastIteration || clusters.equals(lastState);
            lastState = clusters;
            if (shouldTerminate) {
                break;
            }

            // at the end of each iteration we should relocate the centroids
            centroids = relocateCentroids(clusters);
            clusters = new HashMap<>();
        }

        return lastState;

    }
}


