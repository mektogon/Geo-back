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
    private static final String LOGIN_ENDPOINT = "/api/v1/auth/login";

    private static final String[] MOBILE_WHITELIST = {
            "/api/v1/geo/getById/{id}",
            "/api/v1/geo/{name}",
            "/api/v1/geo/web",
            "/api/v1/geo",

            "/api/v1/designation/{id}",
            "/api/v1/audio/{id}",
            "/api/v1/video/{id}",
            "/api/v1/photo/{id}",
            "/api/v1/tail-map/{id}",

            "/api/v1/tail-map",
            "/api/v1/tail-map/getAllByName/{name}"

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
                .antMatchers(ADMIN_ENDPOINT).hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}
