package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
public class UserController {

    private final UserService service;


    @Autowired
    public UserController(UserService service) {
        this.service = service;

    }

    @GetMapping("/user")
    public String getUserProfile(Model model, Principal principal) {
        String email = principal.getName();
        User user = service.findByEmail(email);
        model.addAttribute("user", user);
        model.addAttribute("currentPage", "user");
        return "user";
    }

    @GetMapping("/login")
    public String howFormLogin(@RequestParam(value = "error", required = false) String error,
                               Model model) {
        if (error != null) {
            model.addAttribute("isAuthenticationFailed", true);
            model.addAttribute("errorMessage", "Wrong email or password!");
        }
        return "login";
    }
}
