package id.co.mii.serverapp.controllers;

import java.util.List;


import lombok.AllArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.co.mii.serverapp.models.Role;
import id.co.mii.serverapp.models.User;
import id.co.mii.serverapp.services.UserService;
@AllArgsConstructor
@RestController
@RequestMapping("/user")
@PreAuthorize("hasRole('Admin')")
public class UserController {

    private UserService userService;
    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }
    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping("/{id}")
    public User getById(@PathVariable Integer id) {
        return userService.getById(id);
    }
    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @PostMapping("/add-roles/{id}")
    public User addRole(@PathVariable Integer id, @RequestBody Role role){
        return userService.addRole(id, role);
    }
}
