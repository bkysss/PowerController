package com.nan.mapper;

import com.nan.pojo.PowerCTL;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;



public class PowerCTLMapperImpl extends SqlSessionDaoSupport implements PowerCTLMapper{
    @Override
    public List<PowerCTL> getServInfo() {
        return getSqlSession().getMapper(PowerCTLMapper.class).getServInfo();
    }
}
