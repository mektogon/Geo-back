package ru.dorofeev.mobilemap.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.dorofeev.mobilemap.model.auth.Users;
import ru.dorofeev.mobilemap.repository.UsersRepository;
import ru.dorofeev.mobilemap.service.interf.UsersService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    @Override
    public List<Users> getAll() {
        List<Users> usersList = usersRepository.findAll();
        log.info("В getAll найдено данное количество юзеров: {} ", usersList.size());
        return usersList;
    }

    @Override
    public Users findByLogin(String login) {
        Users result = usersRepository.findByLogin(login);
        log.info("В findByLogin был найден пользователь с логином: {}", login);
        if (result != null) {
            return result;
        } else {
            throw new UsernameNotFoundException("Пользователя с данным логином не существует.");
        }
    }

    @Override
    public Users findById(UUID id) {
        Users result = usersRepository.findById(id).orElse(null);

        if (result == null) {
            throw new NoSuchElementException("Пользователя с таким id не существует.");
        }
        log.info("В findById был найден пользователь с id: {}", result);
        return result;
    }
}
