package com.nan.mapper;

import com.nan.pojo.Exception;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExceptionMapper {
    public List<Exception> getException(@Param("date") String date);
    public String getIp(@Param("date")String date);
}
