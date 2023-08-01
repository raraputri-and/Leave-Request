package id.co.mii.serverapp.services;

import id.co.mii.serverapp.repositories.UserRepository;
import id.co.mii.serverapp.models.Role;
import id.co.mii.serverapp.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    private RoleService roleService;
    private UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
    }

    public User addRole(Integer id, Role role) {

        User user = getById(id);

        List<Role> roles = user.getRoles();
        roles.add(roleService.getById(role.getId()));
        user.setRoles(roles);

        return userRepository.save(user);
    }

}
