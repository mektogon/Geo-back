package ru.dorofeev.mobilemap.service.interf;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.dorofeev.mobilemap.model.auth.Users;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public interface UserService {

    /**
     * Метод возвращает список всех пользователей.
     *
     * @return список пользователей.
     */
    List<Users> getAll();

    /**
     * Поиск пользователя по логину.
     *
     * @param login логин пользователя.
     * @return найденный пользователь.
     * @throws UsernameNotFoundException выбрасывается в том случае, если пользователь был не найден.
     */
    Users findByLogin(String login);

    /**
     * Поиск пользователя по идентификатору.
     *
     * @param id идеинтификатор пользователя.
     * @return найденный пользователь.
     * @throws NoSuchElementException выбрасывается в том случае, если пользователь был не найден.
     */
    Users findById(UUID id);

}
