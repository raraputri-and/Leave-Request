package id.co.mii.clientapp.controllers;

import id.co.mii.clientapp.models.dto.LoginRequest;
import id.co.mii.clientapp.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        System.out.println("login success");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Boolean checkAdmin = authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        if (checkAdmin){
            return "redirect:/emp";
        }
        else {
            return "redirect:/leave-request";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        authService.logout();
        System.out.println("logout");
        return "redirect:/login";
    }
}
