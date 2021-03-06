import com.nan.mapper.DailyMapper;
import com.nan.mapper.ExceptionMapper;
import com.nan.mapper.PowerCTLMapper;
import com.nan.mapper.ServSockMapper;
import com.nan.pojo.Daily;
import com.nan.pojo.Exception;
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
import java.util.Calendar;
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
        ExceptionMapper exceptionMapper=context.getBean("exceptionMapper",ExceptionMapper.class);
        //CalTime.updatePowerCTL("192.168.154.133");
        List<PowerCTL> powerCTLList=powerCTLMapper.getServInfo();
        for (PowerCTL exception : powerCTLList) {
            System.out.println(exception.toString());
        }
        //System.out.println(exceptionMapper.getIp("20210727"));
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
//        int res=CalTime.CalTimeDiff("085800","095900");
//        String a=CalTime.TimeBaseAddDiff(85700,61);
//        System.out.println(a);
        System.out.println(DailyTask.InvertTime("090800"));
    }

    @Test
    public void test8() throws ParseException, InterruptedException {
        String dateStr="20210726";
        String ip="192.168.154.133";
        SimpleDateFormat ft=new SimpleDateFormat("yyyyMMdd");
        Date date=ft.parse(dateStr);
        while(!DailyTask.AnalysisCPU(ip,dateStr)){
            System.out.println("????????????");
            Thread.sleep(5*60*1000);
        }//??????????????????????????????CPU?????????????????????????????????CPU????????????????????????5????????????????????????
        DailyTask.PowerOff(ip);
    }

    @Test
    public void test9() throws ParseException, InterruptedException {
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        ExceptionMapper exceptionMapper=context.getBean("exceptionMapper",ExceptionMapper.class);
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyyMMdd");
        String date=dateFormat.format(calendar.getTime());
        List<Exception> exceptionList=exceptionMapper.getException(date);
        if(exceptionList.size()!=0){ //????????????Exception???????????????????????????????????????????????????PowerControl???????????????
            DailyTask.HandleException(exceptionList);
        }
        else {
            PowerCTLMapper powerCTLMapper=context.getBean("powerCTLMapper",PowerCTLMapper.class);
            List<PowerCTL> powerCTLList=powerCTLMapper.getServInfo();
            DailyTask.HandlePowerCTL(powerCTLList);
        }
    }

}
