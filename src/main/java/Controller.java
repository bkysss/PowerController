import com.nan.mapper.DailyMapper;
import com.nan.mapper.ExceptionMapper;
import com.nan.mapper.PowerCTLMapper;
import com.nan.mapper.ServSockMapper;
import com.nan.pojo.Exception;
import com.nan.pojo.PowerCTL;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Controller {
    private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;
    public static void main(String[] args) throws ParseException, InterruptedException {
        new Controller();
    }
    public Controller() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 1); //凌晨1点
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date date=calendar.getTime(); //第一次执行定时任务的时间
        //如果第一次执行定时任务的时间 小于当前的时间
        //此时要在 第一次执行定时任务的时间加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。
        if (date.before(new Date())) {
            date = this.addDay(date, 1);
        }
        Timer timer = new Timer();
        DailyTask task = new DailyTask();
        //安排指定的任务在指定的时间开始进行重复的固定延迟执行。
        timer.schedule(task,date,PERIOD_DAY);
    }

    //增加天数
    static public Date addDay(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, num);
        return startDT.getTime();
    }

}

class DailyTask extends TimerTask{

    @SneakyThrows
    @Override
    public void run() {
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        ExceptionMapper exceptionMapper=context.getBean("exceptionMapper",ExceptionMapper.class);
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyyMMdd");
        String date=dateFormat.format(calendar.getTime());
        List<Exception> exceptionList=exceptionMapper.getException(date);
        if(exceptionList.size()!=0){ //优先检查Exception表，若有记录则执行，若没有，则根据PowerControl表记录执行
            HandleException(exceptionList);
        }
        else {
            PowerCTLMapper powerCTLMapper=context.getBean("powerCTLMapper",PowerCTLMapper.class);
            List<PowerCTL> powerCTLList=powerCTLMapper.getServInfo();
            HandlePowerCTL(powerCTLList);
        }
    }

    public static void HandleException(List<Exception> exceptionList) throws ParseException, InterruptedException {
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        String date=dateFormat.format(calendar.getTime());
        for(Exception e:exceptionList){
            if(!e.gettOnTime().equals("")){
                TurnOn(e.getIp(),date+" "+InvertTime(e.gettOnTime()));
            }
            if(!e.gettOffTime().equals("")){
                TurnOff(e.getIp(),date+" "+InvertTime(e.gettOffTime()));
            }
        }
    }

    @SneakyThrows
    public static void HandlePowerCTL(List<PowerCTL> powerCTLList){
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        String date=dateFormat.format(calendar.getTime());
        for(PowerCTL p:powerCTLList){
            TurnOn(p.getIP(),date+" "+InvertTime(p.getPOnTime()));
            TurnOff(p.getIP(),date+" "+InvertTime(p.getPOffTime()));
        }
    }

    public static String InvertTime(String str){
        String hour=str.substring(0,2);
        String minute=str.substring(2,4);
        String second=str.substring(4,6);
        return hour+":"+minute+":"+second;
    }

    //服务器开机
    static public void PowerOn(String ip) throws IOException {
//        Socket socket = new Socket("192.168.31.250",2000);
//        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
//        ServSockMapper servSockMapper=context.getBean("servSockMapper",ServSockMapper.class);
//        int turnOnSock=servSockMapper.getSock(ip);
//        String str="SCMD DIGW ";
//        int base=511+turnOnSock;
//        str+=base+" 1 1";
//        BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
//        writer.write(str);
//        writer.flush();
//        socket.close();

        System.out.println(ip+" 开机");
    }

    //服务器关机
    @SneakyThrows
    static public void PowerOff(String ip){
//        Socket socket = new Socket("192.168.31.250",2000);
//        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
//        ServSockMapper servSockMapper=context.getBean("servSockMapper",ServSockMapper.class);
//        int turnOnSock=servSockMapper.getSock(ip);
//        String str="SCMD DIGW ";
//        int base=511+turnOnSock;
//        str+=base+" 1 0";
//        BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
//        writer.write(str);
//        writer.flush();
//        socket.close();

        System.out.println(ip+" 可关机");
    }

    //服务器定时开机功能
    static public void TurnOn(String ip,String dateStr) throws ParseException, InterruptedException {
        Timer timer = new Timer();
        SimpleDateFormat ft=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date=ft.parse(dateStr);
        timer.schedule(new TimerTask() {
            @SneakyThrows
            @Override
            public void run() {
                PowerOn(ip);

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat timeFormat = new SimpleDateFormat("hhmmss");
                String time = timeFormat.format(calendar.getTime());
                System.out.println(time);

                timer.cancel();
            }
        }, date);
    }

    //服务器定时关机功能
    static public void TurnOff(String ip,String dateStr) throws ParseException, InterruptedException {
        Timer timer = new Timer();
        SimpleDateFormat ft=new SimpleDateFormat("yyyyMMdd");
        Date date=ft.parse(dateStr);
        timer.schedule(new TimerTask() {
            @SneakyThrows
            @Override
            public void run() {
                while(!AnalysisCPU(ip,dateStr)){
                    Thread.sleep(5*60*1000);
                }//关机时检查服务器进程CPU使用状态，若有进程占用CPU较多，则进程等待5分钟后再重新检查
                PowerOff(ip);

                timer.cancel();
            }
        }, date);
    }

    //分析CPU字符串数据
    static public boolean AnalysisCPU(String ip,String dateStr){
        String date=dateStr.substring(0,8);
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        DailyMapper dailyMapper=context.getBean("dailyMapper",DailyMapper.class);
        String CPUInfo=dailyMapper.getCPUInfo(date,ip); //当前CPU使用最高top5进程和使用情况，以“；”隔开
        String[] cpuArray=CPUInfo.split(";");  //分别获取各进程CPU使用情况，格式为“进程名|进程最大CPU使用率|进程平均CPU使用率”
        String[] cpuTop=cpuArray[0].split("\\|"); //获取top1进程CPU使用信息

        Double cpuUsage=Double.parseDouble(cpuTop[1]); //获取该进程CPU使用最大值

        if(cpuUsage>10){
            return false;
        }
        else
            return true;
    }
}


