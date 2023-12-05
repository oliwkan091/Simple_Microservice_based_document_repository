package com.Microservice_based_document_repository.Gateway.service;
import com.Microservice_based_document_repository.Gateway.model.User;
import com.Microservice_based_document_repository.Gateway.security.UserLogInPrincipal;
import com.Microservice_based_document_repository.Gateway.security.jwt.JwtManagement;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * Service logic for user authentication
 */
@Service
public class AuthenticationService {

    private final JwtManagement jwtManagement;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(JwtManagement jwtManagement, AuthenticationManager authenticationManager) {
        this.jwtManagement = jwtManagement;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Creates authentication token based on user data
     * @param user data necessary to create token
     * @return String token
     */
    public User singInAndGenerateToken(User user)
    {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));
        UserLogInPrincipal userLogInPrincipal = (UserLogInPrincipal)  authentication.getPrincipal();
        String jwtToken = jwtManagement.generateAuthToken(userLogInPrincipal);
        User signedUser = userLogInPrincipal.getUser();
        signedUser.setToken(jwtToken);
        return signedUser;
    }

}
