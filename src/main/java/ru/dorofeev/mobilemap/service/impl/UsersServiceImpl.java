package ru.dorofeev.mobilemap.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.model.auth.Users;
import ru.dorofeev.mobilemap.repository.UsersRepository;
import ru.dorofeev.mobilemap.service.interf.UserService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UserService {

    private final UsersRepository usersRepository;

    @Override
    public List<Users> getAll() {
        List<Users> usersList = usersRepository.findAll();

        log.debug("IN getAll() - Найдено {} пользователей", usersList.size());

        return usersList;
    }

    @Override
    public Users findByLogin(String login) {
        Users result = usersRepository.findByLogin(login);

        if (result != null) {
            log.debug("IN findByLogin() - Найден пользователь с логином: {}", login);
            return result;
        } else {
            log.debug("IN findByLogin() - Не найден пользователь с логином: {}", login);
            throw new UsernameNotFoundException("Пользователя с данным логином не существует.");
        }
    }

    @Override
    public Users findById(UUID id) {
        Users result = usersRepository.findById(id).orElse(null);

        if (result == null) {
            log.debug("IN findById() - Пользователь с ID: {} не найден!", id);
            throw new NoSuchElementException("Пользователь с таким ID не найден!");
        }

        log.debug("IN findById() - Найден пользователь с ID: {}", result.getId());

        return result;
    }
}
