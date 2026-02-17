package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;


    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAll(Model model, Principal principal) {
        // Текущий пользователь для шапки
        String email = principal.getName();
        User currentUser = userService.findByEmail(email);
        model.addAttribute("user", currentUser);
        // Все пользователи для таблицы
        model.addAttribute("users", userService.getAll());
        //для подсветки меню
        model.addAttribute("currentPage", "admin");

        return "getAll";
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