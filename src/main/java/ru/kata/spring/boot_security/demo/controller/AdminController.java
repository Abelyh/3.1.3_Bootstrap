package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;


    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String getAll(Model model, @AuthenticationPrincipal User currentUser) {
        // Текущий пользователь для шапки
        model.addAttribute("user", currentUser);
        // актуальный список ролей из базы
        model.addAttribute("allRoles", roleService.findAll());
        // Все пользователи для таблицы
        model.addAttribute("users", userService.getAll());
        //для подсветки меню
        model.addAttribute("currentPage", "admin");

        return "admin-page";
    }

    @PostMapping("/create")
    public String createUserAccount(@ModelAttribute("user") User user,
                                    @RequestParam("roleIds") List<Long> roleIds) {
        userService.create(user, roleIds);
        return "redirect:/admin";
    }

    @PostMapping("/update")
    public String updateUser(@RequestParam("id") Long id,
                             @ModelAttribute("user") User user,
                             @RequestParam(value = "roleIds", required = false) List<Long> roleIds) {
        userService.update(id, user, roleIds);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

}