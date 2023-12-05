package com.Microservice_based_document_repository.Gateway.security.jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Creates and inserts filter chain link for incoming requests with specified purpose
 */
public class JwtAuthenticationFilterChain extends OncePerRequestFilter
{
    @Autowired
    JwtManagement jwtManagement;

    /**
     * Creates custom filter chain link
     * @param request incoming request
     * @param response proccesed request passed on to next chain link
     * @param filterChain tree
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        Authentication authentication = jwtManagement.getAuthenticationFromRequest(request);

        if(authentication != null && jwtManagement.isTokenNotExpired(request))
        {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
