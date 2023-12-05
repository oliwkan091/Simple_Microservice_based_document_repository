package com.Microservice_based_document_repository.Gateway.security;
import com.Microservice_based_document_repository.Gateway.model.User;
import com.Microservice_based_document_repository.Gateway.service.UserService;
import com.Microservice_based_document_repository.Gateway.utils.SecurityUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Set;

/**
 * Checks if user existas in database, if user is found creates custom user object to be used in program if user
 * do not exists return exception
 */
@Service
public class UserAuthDetailService implements UserDetailsService {

    private final UserService userService;

    public UserAuthDetailService(UserService userService)
    {
        this.userService = userService;
    }

    /**
     * Checks database for given username, if user exists creates user object to be used across the app, if
     * not returns an exception
     * @param username the username identifying the user whose data is required.
     * @return Custom user object
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.findUserByName(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found with username: " + username));

        Set<GrantedAuthority> authority = Set.of(SecurityUtils.convertRoleToAuthority(user.getRole().name()));

        return UserLogInPrincipal.builder()
                .Id(user.getId())
                .name(username)
                .password(user.getPassword())
                .user(user)
                .authorities(authority)
                .build();
    }
}
