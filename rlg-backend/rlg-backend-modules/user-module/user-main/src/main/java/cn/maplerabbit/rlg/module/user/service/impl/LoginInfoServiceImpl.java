package cn.maplerabbit.rlg.module.user.service.impl;

import cn.maplerabbit.rlg.entity.UserDetails;
import cn.maplerabbit.rlg.enumpak.ServiceCode;
import cn.maplerabbit.rlg.exception.UserException;
import cn.maplerabbit.rlg.module.user.mapper.UserMapper;
import cn.maplerabbit.rlg.module.user.service.ILoginInfoService;
import cn.maplerabbit.rlg.pojo.user.vo.UserLoginVO;
import cn.maplerabbit.rlg.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LoginInfoServiceImpl
        implements ILoginInfoService
{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    HttpServletRequest request;

    public LoginInfoServiceImpl() {log.debug("LoginServiceImpl()...");}

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException
    {
        UserLoginVO loginInfo = userMapper.getLoginInfoByUserName(username);

        log.trace("Get LoginInfo matches username【{}】: {}", username, loginInfo);

        return generateUserDetail(loginInfo);
    }

    @Override
    public UserDetails loadUserByEmail(String email)
    {
        UserLoginVO loginInfo = userMapper.getLoginInfoByEmail(email);

        log.trace("Get LoginInfo matches email【{}】: {}", email, loginInfo);

        return generateUserDetail(loginInfo);
    }

    /**
     * 生成UserDetails
     * @param loginInfo 用户登录信息
     */
    private UserDetails generateUserDetail(UserLoginVO loginInfo)
    {
        if (loginInfo == null)
            throw new UserException(ServiceCode.ERR_BAD_REQUEST, "登录信息有误，请检查输入的账户信息是否正确");

        // 创建权限列表
        Collection<GrantedAuthority> authorities = loginInfo.getPermissions()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // 创建 UserDetails
        UserDetails userDetails = new UserDetails(
                loginInfo.getUuid(),
                loginInfo.getUsername(),
                loginInfo.getPassword(),
                loginInfo.getPhone(),
                loginInfo.getEmail(),
                IpUtil.getIp(request),
                loginInfo.getEnable() == 1,
                authorities
        );

        log.trace("return UserDetails: {}", userDetails);
        return userDetails;
    }
}
