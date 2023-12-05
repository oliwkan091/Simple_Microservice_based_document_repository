package com.Microservice_based_document_repository.Gateway.security.jwt;
import com.Microservice_based_document_repository.Gateway.security.UserLogInPrincipal;
import com.Microservice_based_document_repository.Gateway.utils.SecurityUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;

/**
 * All methods necessary to use JWT. Creates bean
 */
@Component
public class JwtManagement {

    @Value("${app.jwt.secret}")
    private String JWT_SECRET;

    @Value("${app.jwt.expiration}")
    private Long JWT_EXPIRATION;

    /**
     * Creates token base on logged user data
     * @param userLogInPrincipal logged user data
     * @return String JWT token
     */
    public String generateAuthToken(UserLogInPrincipal userLogInPrincipal)
    {
        String authorities = userLogInPrincipal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Key secretKey = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(userLogInPrincipal.getUsername())
                .claim("roles", authorities)
                .claim("userId", userLogInPrincipal.getId())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Gets logged user details form JWT token
     * @param request whole request containing JWT token
     * @return user data extracted from JWT token
     */
    public Authentication getAuthenticationFromRequest(HttpServletRequest request)
    {
        Claims claims = getClaimsFromToken(request);
        if (claims == null)
        {
            return null;
        }

        String name = claims.getSubject();
        Long userId = claims.get("userId", Long.class);

        Set<GrantedAuthority> authorities = Arrays.stream(claims.get("roles").toString().split(","))
                .map(SecurityUtils::convertRoleToAuthority)
                .collect(Collectors.toSet());

        UserDetails userDetails = UserLogInPrincipal.builder()
                .name(name)
                .authorities(authorities)
                .Id(userId)
                .build();

        if(name == null)
        {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

    }

    /**
     * Checks if token is not already expired
     * @param request whole request containing JWT token
     * @return true false token is expierd, if true token is still valid
     */
    public boolean isTokenNotExpired(HttpServletRequest request)
    {
        Claims claims = getClaimsFromToken(request);
        if(claims == null)
        {
            return false;
        }

        if(claims.getExpiration().before(new Date()))
        {
            return false;
        }

        return true;
    }

    /**
     * Convert token to climes
     * @param request whole request containing JWT token
     * @return extracted climes
     */
    private Claims getClaimsFromToken(HttpServletRequest request)  {

        String token = SecurityUtils.extractAuthenticationTokenFromRequest(request);
        if (token == null)
        {
            return null;
        }

        Key secretKey = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
