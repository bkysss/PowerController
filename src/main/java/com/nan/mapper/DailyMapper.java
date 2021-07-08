package com.nan.mapper;

import com.nan.pojo.Daily;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DailyMapper {

    public String getCPUInfo(@Param("date") String date, @Param("ip") String ip);
    public List<String> getTOnFirst(@Param("ip") String ip);
    public List<String> getTOffLast(@Param("ip") String ip);
}
