# 1. 授权 和 认证 @RequiresPermissions(value="")

其工作过程为:

获取登录用户id ->

查询该用户的所有权限 permsSet ->

SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(); -> 

info.setStringPermissions(permsSet); ->
return info;

 ```java
/**
 * Copyright (c) 2016-2019  All rights reserved.
 *
 * https://www.7-me.net
 *
 * 版权所有，侵权必究！
 */

package com.sevenme.modules.sys.oauth2;

import com.sevenme.modules.sys.entity.SysUserEntity;
import com.sevenme.modules.sys.entity.SysUserTokenEntity;
import com.sevenme.modules.sys.service.ShiroService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 认证
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {
    @Autowired
    private ShiroService shiroService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUserEntity user = (SysUserEntity)principals.getPrimaryPrincipal();
        Long userId = user.getUserId();

        //用户权限列表
        Set<String> permsSet = shiroService.getUserPermissions(userId);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accessToken = (String) token.getPrincipal();

        //根据accessToken，查询用户信息
        SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
        //token失效
        if(tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()){
            throw new IncorrectCredentialsException("token失效，请重新登录");
        }

        //查询用户信息
        SysUserEntity user = shiroService.queryUser(tokenEntity.getUserId());
        //账号锁定
        if(user.getStatus() == 0){
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, accessToken, getName());
        return info;
    }
}

 ```



1. 注解 @Syslog 用在 方法上, 可以记录日志;

2. 定时任务

```tex
sys:schedule:list
sys:schedule:delete

```

3. 权限

|                     |              |      |
| ------------------- | ------------ | ---- |
| sys:schedule:list   |              |      |
| sys:schedule:info   |              |      |
| sys:schedule:save   |              |      |
| sys:schedule:update |              |      |
| sys:schedule:delete |              |      |
| sys:schedule:run    | 立即执行任务 |      |
| sys:schedule:pause  |              |      |
| sys:schedule:resume | 恢复定时任务 |      |
|                     |              |      |
|                     |              |      |
|                     |              |      |
|                     |              |      |
|                     |              |      |





```tex
        sys:config:delete,
        sys:config:info,
        sys:config:list,
        sys:config:save,
        sys:config:update,
        sys:log:list,
        sys:menu:delete,
        sys:menu:info,
        sys:menu:list,
        sys:menu:save,
        sys:menu:select,
        sys:menu:update,
        sys:oss:all
        sys:role:delete,
        sys:role:info,
        sys:role:list,
        sys:role:save,
        sys:role:select,
        sys:role:update,
        sys:schedule:delete,
        sys:schedule:list,
        sys:schedule:log,
        sys:schedule:pause,
        sys:schedule:resume,
        sys:schedule:run,
        sys:schedule:save,
        sys:schedule:update,
        sys:user:delete,
        sys:user:info,
        sys:user:list,
        sys:user:save,
        sys:user:update,
sys:schedule:info,


```



```tex
 'sys:schedule:info',
        'sys:menu:update',
        'sys:menu:delete',
        'sys:config:info',
        'sys:menu:list',
        'sys:config:save',
        'sys:config:update',
        'sys:schedule:resume',
        'sys:user:delete',
        'sys:config:list',
        'sys:user:update',
        'sys:role:list',
        'sys:menu:info',
        'sys:menu:select',
        'sys:schedule:update',
        'sys:schedule:save',
        'sys:role:select',
        'sys:user:list',
        'sys:menu:save',
        'sys:role:save',
        'sys:schedule:log',
        'sys:role:info',
        'sys:schedule:delete',
        'sys:role:update',
        'sys:schedule:list',
        'sys:user:info',
        'sys:schedule:run',
        'sys:config:delete',
        'sys:role:delete',
        'sys:user:save',
        'sys:schedule:pause',
        'sys:log:list',
        'sys:oss:all'
```



# 2. 问题

1. 上级菜单的路由修改后, 下级路由没有跟着变化
2. 菜单管理的新增中, 授权标识 部分 感觉 设计的不太好, 
3. 同样, 菜单路由做成选择框会更好点
4. 菜单管理中, 排序号是干嘛的?
5. 