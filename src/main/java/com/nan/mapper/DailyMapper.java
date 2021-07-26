package com.nan.mapper;

import com.nan.pojo.Daily;
import com.nan.pojo.TimeInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.nan.pojo.Record;

public interface DailyMapper {

    public String getCPUInfo(@Param("date") String date, @Param("ip") String ip);
    public List<String> getTOnFirst(@Param("ip") String ip);
    public List<String> getTOffLast(@Param("ip") String ip);
    public List<TimeInfo> getTimeInfo(@Param("ip") String ip);
    public List<Daily> getServDaily(@Param("ip") String ip);
    public String getServMinTOn(@Param("ip") String ip);
    public String getServMinTOff(@Param("ip") String ip);
}
