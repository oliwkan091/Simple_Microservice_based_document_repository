package com.Microservice_based_document_repository.Gateway.utils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;

/**
 * Utils necessary for security
 */
public class SecurityUtils {

    public static final String ROLE = "ROLE_";
    public static final String AUTH_HEADER = "authorization";
    public static final String AUTH_TOKEN_TYPE = "Bearer";

    /**
     * Addes prefix to role
     * @param role role without prefix
     * @return role with prefix
     */
    public static SimpleGrantedAuthority convertRoleToAuthority(String role)
    {
        String roleWithPrefix = role.startsWith(ROLE) ? role : ROLE + role;
        return new SimpleGrantedAuthority(roleWithPrefix);
    }

    /**
     * Return token form http request
     * @param request http request with token
     * @return token extracted form request
     */
    public static String extractAuthenticationTokenFromRequest(HttpServletRequest request)
    {
        String token = request.getHeader(AUTH_HEADER);

        if(StringUtils.hasLength(token) && token.startsWith(AUTH_TOKEN_TYPE) && token.length() >= 7)
        {
            return token.substring(AUTH_TOKEN_TYPE.length() + 1);
        }

        return null;
    }

}
