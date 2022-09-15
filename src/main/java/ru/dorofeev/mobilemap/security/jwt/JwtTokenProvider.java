package ru.dorofeev.mobilemap.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.dorofeev.mobilemap.exception.JwtAuthenticationException;
import ru.dorofeev.mobilemap.model.auth.Role;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    /**
     * Переменная для кодирования подписи токена.
     */
    @Value("${jwt.token.secret}")
    private String secret;

    /**
     * Переменная, которая задает длительность валидности токена.
     */
    @Value("${jwt.token.expired}")
    private long validationInMilliseconds;

    private final UserDetailsService userDetailsService;

    public JwtTokenProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    /**
     * Метод, который создает токен.
     *
     * @param username Имя пользователя для payload токена.
     * @param role     Роль пользователя для payload токена.
     * @return Возвращает строку - токен.
     */
    public String createToken(String username, Role role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", getRoleNames(role));

        Date now = new Date();
        Date validate = new Date(now.getTime() + validationInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * @return Возвращает аутентификацию.
     */
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


    /**
     * Метод для получения имени из токена для аутентификации.
     *
     * @param token из которого извлекается имя пользователя.
     * @return строку - имя пользователя.
     */
    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Метод для проверки корректности токена.
     *
     * @param request Запрос.
     * @return возвращает токен, полученный из запроса.
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer_")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    /**
     * Метод определяет валидность токена.
     *
     * @param token - токен.
     * @return Возвращает ответ true, если токен валиден.
     * @throws JwtAuthenticationException выбрасывается в случае, если срок действия токена истек или он невалиден.
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());

        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }

    }

    /**
     * Метод возвращает строковое представление ролей пользователя.
     *
     * @return список ролей.
     */
    private List<String> getRoleNames(Role userRole) {
        List<String> result = new ArrayList<>();
        result.add(userRole.getName());
        return result;
    }
}
