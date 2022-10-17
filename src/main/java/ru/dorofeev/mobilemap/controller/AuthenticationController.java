package ru.dorofeev.mobilemap.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dorofeev.mobilemap.model.auth.Users;
import ru.dorofeev.mobilemap.model.dto.AuthenticationRequestDto;
import ru.dorofeev.mobilemap.security.jwt.JwtTokenProvider;
import ru.dorofeev.mobilemap.service.interf.UsersService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsersService usersService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String login = requestDto.getUsername();

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, requestDto.getPassword()));
            Users user = usersService.findByLogin(login);

            if (user == null) {
                throw new UsernameNotFoundException("Пользователь с username: " + login + " не найден!");
            }

            String token = jwtTokenProvider.createToken(login, user.getRole());

            Map<Object, Object> response = new HashMap<>();
            response.put("username", login);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Неправильный логин или пароль!");
        }
    }

}
