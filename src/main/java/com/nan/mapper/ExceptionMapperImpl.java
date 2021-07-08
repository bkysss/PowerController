package com.nan.mapper;

import com.nan.pojo.Exception;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

public class ExceptionMapperImpl extends SqlSessionDaoSupport implements ExceptionMapper{
    @Override
    public List<Exception> getException(String date) {
        return getSqlSession().getMapper(ExceptionMapper.class).getException(date);
    }
}
