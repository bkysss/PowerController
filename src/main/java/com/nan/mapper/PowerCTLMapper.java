package com.nan.mapper;

import com.nan.pojo.PowerCTL;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PowerCTLMapper {
    public List<PowerCTL> getServInfo();
    public void insertInfo(PowerCTL powerCTL);
    public int HasInfo(@Param("ip") String ip);
    public void updateInfo(PowerCTL powerCTL);

}
