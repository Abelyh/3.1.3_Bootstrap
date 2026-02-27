package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    @GetMapping("/user")
    public String getUserProfile(Model model, @AuthenticationPrincipal User currentUser) {
        model.addAttribute("user", currentUser);
        //для подсветки меню
        model.addAttribute("currentPage", "user");
        return "user-page";
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
