package com.tcvideo.shiro;

import com.tcvideo.user.entity.Permission;
import com.tcvideo.user.entity.Role;
import com.tcvideo.user.entity.User;
import com.tcvideo.user.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by caiteng on 2017-11-10.
 */
class UserRealm extends AuthorizingRealm {
    // 用户对应的角色信息与权限信息都保存在数据库中，通过UserService获取数据
    @Autowired
    private UserService userService;

    /**
     * 授予权限
     * 提供用户信息返回权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("授权");
        String username = (String) principals.getPrimaryPrincipal();

        // 根据用户名查询当前用户拥有的角色
        Set<Role> roles = userService.findRoles(username);
        Set<String> roleNames = new HashSet<String>();
        for (Role role : roles) {
            roleNames.add(role.getName());
        }
        // 将角色名称提供给info
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo(roleNames);

        // 根据角色id查询当前用户权限
        Set<Permission> permissions = userService.findPermissions(roles);
        Set<String> permissionNames = new HashSet<String>();
        for (Permission permission : permissions) {
            permissionNames.add(permission.getUrl());
        }
        // 将权限名称提供给info
        authorizationInfo.setStringPermissions(permissionNames);

        return authorizationInfo;
    }

    /**
     * 提供账户信息返回认证信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("认证");
        String username = (String) token.getPrincipal();
        User user = userService.findByUsername(username);
        if (user == null) {
            // 用户名不存在抛出异常
            throw new UnknownAccountException();
        }
        if (user.getStatus() == 0) {
            // 用户被管理员锁定抛出异常
            throw new LockedAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getName(),
                user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
        return authenticationInfo;
    }
}
