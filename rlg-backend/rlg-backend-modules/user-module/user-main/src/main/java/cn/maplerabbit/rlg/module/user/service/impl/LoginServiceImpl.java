package cn.maplerabbit.rlg.module.user.service.impl;

import cn.maplerabbit.rlg.module.user.mapper.UserMapper;
import cn.maplerabbit.rlg.module.user.service.ILoginService;
import cn.maplerabbit.rlg.pojo.user.vo.UserLoginVO;
import cn.maplerabbit.rlg.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import cn.maplerabbit.rlg.entity.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LoginServiceImpl implements ILoginService
{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    HttpServletRequest request;

    public LoginServiceImpl() {log.debug("LoginServiceImpl()...");}

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Spring Security call loadUserByUsername(), arg: {}", username);

        UserLoginVO loginInfo = userMapper.getLoginInfoByUserName(username);
        if (loginInfo == null) {return null;}

        log.debug("get LoginInfo matches username【{}】: {}", username, loginInfo);

        // 创建权限列表
        Collection<GrantedAuthority> authorities = loginInfo.getPermissions()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // 创建 UserDetails
        UserDetails userDetails = new UserDetails(
                loginInfo.getId(),
                loginInfo.getUsername(),
                loginInfo.getPassword(),
                loginInfo.getPhone(),
                loginInfo.getEmail(),
                IpUtil.getIp(request),
                loginInfo.getEnable() == 1,
                authorities
        );

        log.debug("return UserDetails: {}", userDetails);
        return userDetails;
    }

}
