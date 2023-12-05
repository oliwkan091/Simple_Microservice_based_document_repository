package com.Microservice_based_document_repository.Gateway.security;
import com.Microservice_based_document_repository.Gateway.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Set;

/**
 * Necessary logged user details to be used to authenticate user
 */
@Getter
@Setter
@Builder
public class UserLogInPrincipal implements UserDetails {

    private Long Id;
    private String name;
    transient private String password;
    transient private User user;

    private Set<GrantedAuthority> authorities;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return authorities;
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    @Override
    public String getUsername()
    {
        return name;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }
}
