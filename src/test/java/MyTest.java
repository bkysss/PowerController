import com.nan.mapper.DailyMapper;
import com.nan.mapper.PowerCTLMapper;
import com.nan.pojo.Daily;
import com.nan.pojo.PowerCTL;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class MyTest {
    @Test
    public void test(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        DailyMapper dailyMapper=context.getBean("dailyMapper",DailyMapper.class);
//        List<Daily> dailyList= dailyMapper.getDaily();
//        for(Daily daily:dailyList){
//            System.out.println(daily);
//        }
        List<String> str=dailyMapper.getTOnFirst("192.168.154.133");
        //System.out.println(cpu);
        for(String s:str){
            System.out.println(s);
        }
    }

    @Test
    public void test1(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        PowerCTLMapper powerCTLMapper=context.getBean("powerCTLMapper",PowerCTLMapper.class);
        List<PowerCTL> list=powerCTLMapper.getServInfo();
        for(PowerCTL powerCTL:list){
            System.out.println(powerCTL.getIP());
            System.out.println(powerCTL);
        }
    }
}
