package ru.dorofeev.mobilemap.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.model.auth.Users;
import ru.dorofeev.mobilemap.security.jwt.JwtUser;
import ru.dorofeev.mobilemap.security.jwt.JwtUserFactory;
import ru.dorofeev.mobilemap.service.interf.UsersService;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final UsersService usersService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Users user = usersService.findByLogin(login);

        JwtUser jwtUser = JwtUserFactory.create(user);

        log.info("IN loadUserByUsername() - Пользователь с логином: {} успешно загружен", login);

        return jwtUser;
    }
}
