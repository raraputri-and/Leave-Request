package id.co.mii.clientapp.controllers;

import id.co.mii.clientapp.models.Role;
import id.co.mii.clientapp.services.RoleService;
import id.co.mii.clientapp.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/role")
public class RoleController {
    private RoleService roleService;
    private UserService userService;

    @GetMapping
    public String index(Model model){
        model.addAttribute("roles", userService.getAll());
        model.addAttribute("title", "role");
        return "Admin/role";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Integer id, Role role){
        roleService.update(id,role);
        return "redirect:/role";
    }
}
