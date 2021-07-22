package com.nan.mapper;

import com.nan.pojo.PowerCTL;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;



public class PowerCTLMapperImpl extends SqlSessionDaoSupport implements PowerCTLMapper{
    @Override
    public List<PowerCTL> getServInfo() {
        return getSqlSession().getMapper(PowerCTLMapper.class).getServInfo();
    }


    @Override
    public void insertInfo(PowerCTL powerCTL) {
        getSqlSession().getMapper(PowerCTLMapper.class).insertInfo(powerCTL);
    }

    @Override
    public int HasInfo(String ip) {
        return getSqlSession().getMapper(PowerCTLMapper.class).HasInfo(ip);
    }

    @Override
    public void updateInfo(PowerCTL powerCTL) {
        getSqlSession().getMapper(PowerCTLMapper.class).updateInfo(powerCTL);
    }
}
