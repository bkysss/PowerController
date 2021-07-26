package com.nan.mapper;

import com.nan.pojo.Daily;
import com.nan.pojo.Record;
import com.nan.pojo.TimeInfo;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

public class DailyMapperImpl extends SqlSessionDaoSupport implements DailyMapper{

    @Override
    public String getCPUInfo(String DDate, String ip) {
        return getSqlSession().getMapper(DailyMapper.class).getCPUInfo(DDate,ip);
    }

    @Override
    public List<String> getTOnFirst(String ip) {
        return getSqlSession().getMapper(DailyMapper.class).getTOnFirst(ip);
    }

    @Override
    public List<String> getTOffLast(String ip) {
        return getSqlSession().getMapper(DailyMapper.class).getTOffLast(ip);
    }

    @Override
    public String getServMinTOff(String ip) {
        return getSqlSession().getMapper(DailyMapper.class).getServMinTOff(ip);
    }

    @Override
    public List<TimeInfo> getTimeInfo(String ip) {
        return getSqlSession().getMapper(DailyMapper.class).getTimeInfo(ip);
    }

    @Override
    public String getServMinTOn(String ip) {
        return getSqlSession().getMapper(DailyMapper.class).getServMinTOn(ip);
    }

    @Override
    public List<Daily> getServDaily(String ip) {
        return getSqlSession().getMapper(DailyMapper.class).getServDaily(ip);
    }
}
