package com.tcvideo.user.dao;


import com.tcvideo.user.entity.RolePermission;

import java.util.List;

public interface RolePermissionMapper {
    int insert(RolePermission record);

    int insertSelective(RolePermission record);

    List<RolePermission> findermissionByRoleId(Integer id);
}