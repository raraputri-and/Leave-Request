package id.co.mii.clientapp.controllers;

import id.co.mii.clientapp.models.dto.LoginRequest;
import id.co.mii.clientapp.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
@AllArgsConstructor
public class AuthController {

    private AuthService authService;

    @GetMapping
    public String loginView(LoginRequest loginRequest) {
        return "Auth/login";
    }

    @PostMapping
    public String login(LoginRequest loginRequest) {
        if (!authService.login(loginRequest)) {
            return "redirect:/login?error=true";
        }
        ;
        System.out.println("login success");
        return "redirect:/leave-request";
    }

    @GetMapping("/logout")
    public String logout() {
        authService.logout();
        System.out.println("logout");
        return "redirect:/login";
    }
}
