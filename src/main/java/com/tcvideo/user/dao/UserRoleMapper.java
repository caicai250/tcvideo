package com.tcvideo.user.dao;


import com.tcvideo.user.entity.UserRole;

import java.util.List;

public interface UserRoleMapper {
    int insert(UserRole record);

    int insertSelective(UserRole record);

    List<UserRole> findRoleByUserId(int user_id);
}