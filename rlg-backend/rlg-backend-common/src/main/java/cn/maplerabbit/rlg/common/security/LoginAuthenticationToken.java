package cn.maplerabbit.rlg.common.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

import java.util.Collection;

public class LoginAuthenticationToken
        extends AbstractAuthenticationToken
{
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Object principal;

    private Object credentials;

    private String loginWay;

    public LoginAuthenticationToken(Object principal, Object credentials, String loginWay)
    {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.loginWay = loginWay;
        setAuthenticated(false);
    }

    public LoginAuthenticationToken(
            Object principal,
            Object credentials,
            Collection<? extends GrantedAuthority> authorities
    )
    {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true); // must use super, as we override
    }

    @Override
    public Object getCredentials()
    {
        return this.credentials;
    }

    @Override
    public Object getPrincipal()
    {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated)
            throws IllegalArgumentException
    {
        Assert.isTrue(
                !isAuthenticated,
                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead"
        );
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials()
    {
        super.eraseCredentials();
        this.credentials = null;
    }

    public String getLoginWay()
    {
        return loginWay;
    }
}