package com.nan.mapper;

import com.nan.pojo.Daily;
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
}
