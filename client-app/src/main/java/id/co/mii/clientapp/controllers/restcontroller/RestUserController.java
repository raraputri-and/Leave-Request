package id.co.mii.clientapp.controllers.restcontroller;

import id.co.mii.clientapp.models.User;
import id.co.mii.clientapp.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class RestUserController {
    private UserService userService;

    @GetMapping
    public List<User> getAll(){
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Integer id){
        return userService.getById(id);
    }

    @PutMapping("/update/{id}")
    public User update(@PathVariable Integer id, @RequestParam Integer roleId){
        return userService.update(id,roleId);
    }
}
