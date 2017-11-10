package com.tcvideo.user.service;

import com.tcvideo.user.dao.*;
import com.tcvideo.user.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017-07-25.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    public User get(int id)  {
        User users = userMapper.selectByPrimaryKey(id);
        return users;
    }

    /**
     * 根据用户名查询用户
     * @param name
     * @return
     */
    public User getByName(String name){
        return userMapper.getByName(name);
    }

    public List<User> getAll(){
       return userMapper.getAll();
    }

    public Set<Role> findRoles(String username) {
        Set<Role> roles=new HashSet<Role>();
        List<UserRole> UserRoles= userRoleMapper.findRoleByUserId(getByName(username).getId());
        for(UserRole userRole : UserRoles){
            roles.add(roleMapper.selectByPrimaryKey(userRole.getrId()));
        }
        return roles;
    }

    public Set<Permission> findPermissions(Set<Role> roles) {
        Set<Permission> permissions=new HashSet<Permission>();
        for(Role role:roles) {
            List<RolePermission> r_ps=rolePermissionMapper.findermissionByRoleId(role.getId());
            for(RolePermission r_p:r_ps) {
                permissions.add(permissionMapper.selectByPrimaryKey(r_p.getpId()));
            }
        }
        return permissions;
    }

    public User findByUsername(String username) {
        return userMapper.getByName(username);
    }

}
