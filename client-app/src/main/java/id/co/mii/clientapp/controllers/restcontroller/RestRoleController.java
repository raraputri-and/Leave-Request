package id.co.mii.clientapp.controllers.restcontroller;

import id.co.mii.clientapp.models.Role;
import id.co.mii.clientapp.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
@AllArgsConstructor
public class RestRoleController {
    private RoleService roleService;

    @GetMapping
    public List<Role> getAll(){
        return roleService.getAll();
    }

    @GetMapping("/{id}")
    public Role getById(@PathVariable Integer id){
        return roleService.getById(id);
    }

    @PutMapping("/update/{id}")
    public Role update(@PathVariable Integer id, @RequestBody Role role){
        return roleService.update(id,role);
    }
}
