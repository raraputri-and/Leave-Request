package id.co.mii.serverapp.controllers;

import id.co.mii.serverapp.models.JointLeave;
import id.co.mii.serverapp.services.JointLeaveService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/jointleave")
public class JointLeaveController {
    private JointLeaveService jointLeaveService;
    //    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping
    public List<JointLeave> getAll() {
        return jointLeaveService.getAll();
    }
    //    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping("/{id}")
    public JointLeave getById(@PathVariable Integer id) {
        return jointLeaveService.getById(id);
    }
    //    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
    @PostMapping
    public JointLeave create(@RequestBody JointLeave jointLeave) {
        return jointLeaveService.create(jointLeave);
    }
    //    @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
    @PutMapping("/{id}")
    public JointLeave update(@PathVariable Integer id, @RequestBody JointLeave jointLeave) {
        return jointLeaveService.update(id, jointLeave);
    }
    //    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    @DeleteMapping("/{id}")
    public JointLeave delete(@PathVariable Integer id) {
        return jointLeaveService.delete(id);
    }
}
