import com.nan.mapper.DailyMapper;
import com.nan.mapper.PowerCTLMapper;
import com.nan.mapper.ServSockMapper;
import com.nan.pojo.Daily;
import com.nan.pojo.PowerCTL;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

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

    @Test
    public void test5(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        PowerCTLMapper powerCTLMapper=context.getBean("powerCTLMapper",PowerCTLMapper.class);
        int res= powerCTLMapper.HasInfo("192.168.154.133");
        //String tOntime=""+ CalTime.calTOnTime(CalTime.getServTime("192.168.154.133")).intValue();
        System.out.println("tOntime");
    }

    @Test
    public void test6(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        DailyMapper dailyMapper=context.getBean("dailyMapper",DailyMapper.class);
        String s=dailyMapper.getServMinTOff("192.168.154.133");
        System.out.println(s);
    }

    @Test
    public void test7(){
        int res=CalTime.CalTimeDiff("085800","095900");
        String a=CalTime.TimeBaseAddDiff(85700,61);
        System.out.println(a);
    }

    @Test
    public void test8() throws ParseException, InterruptedException {
        String dateStr="20210726";
        String ip="192.168.154.133";
        SimpleDateFormat ft=new SimpleDateFormat("yyyyMMdd");
        Date date=ft.parse(dateStr);
        while(!DailyTask.AnalysisCPU(ip,dateStr)){
            System.out.println("不能关机");
            Thread.sleep(5*60*1000);
        }//关机时检查服务器进程CPU使用状态，若有进程占用CPU较多，则进程等待5分钟后再重新检查
        DailyTask.PowerOff(ip);
    }

}
