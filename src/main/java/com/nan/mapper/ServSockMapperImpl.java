package com.nan.mapper;

import org.mybatis.spring.support.SqlSessionDaoSupport;

public class ServSockMapperImpl extends SqlSessionDaoSupport implements ServSockMapper {
    @Override
    public int getSock(String ip) {
        return getSqlSession().getMapper(ServSockMapper.class).getSock(ip);
    }
}
