package com.wl.travel.dao;

import com.wl.travel.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {
    UserEntity selectById(@Param("id") Long id);
}
