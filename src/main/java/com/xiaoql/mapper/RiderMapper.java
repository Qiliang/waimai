package com.xiaoql.mapper;

import com.xiaoql.entity.Rider;
import com.xiaoql.entity.RiderExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface RiderMapper {
    long countByExample(RiderExample example);

    int deleteByExample(RiderExample example);

    int deleteByPrimaryKey(String id);

    int insert(Rider record);

    int insertSelective(Rider record);

    List<Rider> selectByExample(RiderExample example);

    Rider selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Rider record, @Param("example") RiderExample example);

    int updateByExample(@Param("record") Rider record, @Param("example") RiderExample example);

    int updateByPrimaryKeySelective(Rider record);

    int updateByPrimaryKey(Rider record);
}