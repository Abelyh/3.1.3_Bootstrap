package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;


public interface UserService {

    void create(User user, List<Long> roleIds);

    List<User> getAll();

     void update(Long id, User updateUser,  List<Long> roleIds);

    void delete(Long id);

    User findById(Long id);

    User findByEmail(String email);

}
