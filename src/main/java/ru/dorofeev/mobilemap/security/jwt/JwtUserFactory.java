package ru.dorofeev.mobilemap.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.dorofeev.mobilemap.model.auth.Role;
import ru.dorofeev.mobilemap.model.auth.Users;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class JwtUserFactory {
    public JwtUserFactory() {
    }

    /**
     * Создание пользователя для работы с Spring Security.
     *
     * @param user - Объект класса User, используемый для создания пользователя для JWT
     * @return Jwt пользователь
     */
    public static JwtUser create(Users user) {
        HashSet hashSet = new HashSet<>();
        hashSet.add(user.getRole());
        return new JwtUser(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                mapToGrantedAuthorities(hashSet)
        );
    }

    /**
     * Метод конвертирует роль User пользователя в authorities JWT пользователя
     *
     * @param userRoles Роль пользователя User
     * @return authorities JwtUser
     */
    private static List<GrantedAuthority> mapToGrantedAuthorities(Set<Role> userRoles) {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
