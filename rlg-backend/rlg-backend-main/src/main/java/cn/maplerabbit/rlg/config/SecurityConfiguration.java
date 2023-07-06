package cn.maplerabbit.rlg.config;

import cn.maplerabbit.rlg.common.property.TokenProperties;
import cn.maplerabbit.rlg.common.property.LoginProperties;
import cn.maplerabbit.rlg.common.security.LoginAuthenticationFailHandler;
import cn.maplerabbit.rlg.common.security.LoginAuthenticationFilter;
import cn.maplerabbit.rlg.common.security.LoginAuthenticationProvider;
import cn.maplerabbit.rlg.common.util.ValidationCodeUtil;
import cn.maplerabbit.rlg.filter.CodeObtainFilter;
import cn.maplerabbit.rlg.filter.TokenAuthorizationFilter;
import cn.maplerabbit.rlg.common.property.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security配置类
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration
        extends WebSecurityConfigurerAdapter
{
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private LoginAuthenticationProvider loginAuthenticationProvider;
    @Autowired
    private LoginAuthenticationFailHandler loginAuthenticationFailHandler;
    @Autowired
    private LoginProperties loginProperties;
    @Autowired
    private TokenProperties tokenProperties;
    @Autowired
    private ValidationCodeUtil validationCodeUtil;

    public SecurityConfiguration() {log.debug("SecurityConfiguration()...");}

    // 加密编码器
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    // 认证管理器
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean()
            throws Exception
    {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http)
            throws Exception
    {
        TokenAuthorizationFilter tokenAuthorizationFilter = new TokenAuthorizationFilter(tokenProperties);
        CodeObtainFilter codeObtainFilter = new CodeObtainFilter(validationCodeUtil);
        LoginAuthenticationFilter loginAuthenticationFilter =
                new LoginAuthenticationFilter(
                        loginProperties,
                        authenticationManager()
                );

        loginAuthenticationFilter.setAuthenticationFailureHandler(loginAuthenticationFailHandler);

        http
                // 启用Security框架自带的CorsFilter过滤器，对OPTIONS请求放行
                .cors()
                .and()
                // 配置请求是否需要认证
                .authorizeRequests()
                .mvcMatchers(securityProperties.getUrlAllowlist())  // 匹配白名单中的请求路径
                .permitAll()        // 允许直接访问
                .anyRequest()       // 其它任何请求
                .authenticated()    // 需要通过认证
                .and()
                // 禁用“防止伪造的跨域攻击”防御机制
                .csrf()
                .disable()
                .authenticationProvider(loginAuthenticationProvider)
                // 将JWT过滤器置于Spring Security的“用户名密码认证信息过滤器”之前
                .addFilterBefore(
                        tokenAuthorizationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                // 将验证码获取过滤器置于JWT过滤器之前
                .addFilterBefore(
                        codeObtainFilter,
                        TokenAuthorizationFilter.class
                )
                // 用通用登录验证过滤器替换security自带的用户名密码认证信息过滤器
                .addFilterAt(
                        loginAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                // 设置Session创建策略：从不创建
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER);
    }
}