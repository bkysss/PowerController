package com.nan.mapper;

import org.apache.ibatis.annotations.Param;

public interface ServSockMapper {
    public int getSock(@Param("ip") String ip);
}
