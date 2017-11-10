package com.tcvideo.user.service;


import com.tcvideo.user.entity.Permission;
import com.tcvideo.user.entity.Role;
import com.tcvideo.user.entity.User;

import java.util.List;
import java.util.Set;

/**
 * Created by caiteng on 2017-11-10.
 */
public interface UserService {

    User get(int id);

    List<User> getAll();

    Set<Role> findRoles(String username);

    Set<Permission> findPermissions(Set<Role> roles);

    User findByUsername(String username);
}
