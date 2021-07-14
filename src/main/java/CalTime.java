import com.nan.mapper.DailyMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class CalTime {
    ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
    DailyMapper dailyMapper=context.getBean("dailyMapper",DailyMapper.class);
    public static void main(String[] args) {

    }
    public String calTurnOnTime(List<String> timeList){
        int[] array=new int[timeList.size()];
        int num=0;
        for(String time:timeList){
            array[num]=Integer.parseInt(time);
            num++;
        }
        String args[]=new String[timeList.size()+2];
        args[0]="python";
        args[1]="main.py";
        return "";
    }
}
