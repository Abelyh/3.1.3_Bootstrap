package ru.kata.spring.boot_security.demo.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void create(User user, List<Long> roleIds) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> allById = roleService.findAllById(roleIds);
        user.setRoles(allById);
        userRepository.save(user);

    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void update(Long id, User updateUser, List<Long> roleIds) {
        User userById = findById(id);
        userById.setFirstName(updateUser.getFirstName());
        userById.setLastName(updateUser.getLastName());
        userById.setAge(updateUser.getAge());
        userById.setEmail(updateUser.getEmail());
        Set<Role> roles = roleService.findAllById(roleIds);
        userById.setRoles(roles);
        if (!updateUser.getPassword().isEmpty()) {
            userById.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        }
        userRepository.save(userById);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User userById = findById(id);
        userRepository.delete(userById);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Not found"));
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity with id " + id + " not found"));
    }
}
