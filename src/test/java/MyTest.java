import com.nan.mapper.DailyMapper;
import com.nan.mapper.PowerCTLMapper;
import com.nan.mapper.ServSockMapper;
import com.nan.pojo.Daily;
import com.nan.pojo.PowerCTL;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
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
        List<Daily> str=dailyMapper.getServDaily("192.168.154.133");
        //System.out.println(cpu);
        for(Daily s:str){
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

    @Test
    public void test2() throws IOException {
        Socket socket=new Socket("192.168.31.250",2000);
        String s="SCMD DIGW 512 1 1";
        BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        writer.write(s);
        writer.flush();
        socket.close();
        System.out.println("succeed");
    }

    @Test
    public void test3(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        ServSockMapper servSockMapper=context.getBean("servSockMapper",ServSockMapper.class);
        int res=servSockMapper.getSock("192.168.154.133");
        System.out.println(res);
    }

    @Test
    public void test4(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        PowerCTLMapper powerCTLMapper=context.getBean("powerCTLMapper",PowerCTLMapper.class);
        CalTime.updatePowerCTL("192.168.154.133");
    }
}
