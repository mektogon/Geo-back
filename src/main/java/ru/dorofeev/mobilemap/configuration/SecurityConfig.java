package ru.dorofeev.mobilemap.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import ru.dorofeev.mobilemap.security.jwt.JwtConfigurer;
import ru.dorofeev.mobilemap.security.jwt.JwtTokenProvider;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtTokenProvider jwtTokenProvider;

    private static final String ADMIN_ENDPOINT = "/api/v1/**";
    private static final String HEALTH_ENDPOINT = "/actuator/health";
    private static final String[] TILE_EDITOR_ENDPOINT = {
            "/api/v1/tile-map/**",
            "/api/v1/road/**",
            "/api/v1/manifesto/**",
    };
    private static final String LOGIN_ENDPOINT = "/api/v1/auth/login";

    private static final String[] MOBILE_WHITELIST = {
            "/api/v1/geo/**",
            "/api/v1/designation/**",
            "/api/v1/audio/view/**",
            "/api/v1/video/view/**",
            "/api/v1/photo/view/**",
            "/api/v1/photo/**",
            "/api/v1/tile-map/**",
            "/api/v1/road/**",
            "/api/v1/manifesto/**",
    };
    
    private static final String[] SWAGGER_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
    };


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()

                .mvcMatchers(LOGIN_ENDPOINT).permitAll()
                .mvcMatchers(HttpMethod.GET, MOBILE_WHITELIST).permitAll()
                .mvcMatchers(SWAGGER_WHITELIST).permitAll()
                .mvcMatchers(HEALTH_ENDPOINT).permitAll()
                .mvcMatchers(TILE_EDITOR_ENDPOINT).hasAnyRole("TILE_EDITOR", "ADMIN")
                .antMatchers(ADMIN_ENDPOINT).hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}
