package com.xu.myWechat.mapper;

import com.xu.myWechat.pojo.dao.BotX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BotXMapper {

    @Select("select * from botx where id=#{id}")
    BotX findById(@Param("id") Integer id);
}
