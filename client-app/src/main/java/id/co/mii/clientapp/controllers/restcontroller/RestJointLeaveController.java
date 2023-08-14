package id.co.mii.clientapp.controllers.restcontroller;

import id.co.mii.clientapp.models.JointLeave;
import id.co.mii.clientapp.services.JointLeaveService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/joint-leave")
@AllArgsConstructor
public class RestJointLeaveController {
    private JointLeaveService jointLeaveService;

    @GetMapping
    public List<JointLeave> getAll(){
        return jointLeaveService.getAll();
    }

    @GetMapping("/{id}")
    public JointLeave getById(@PathVariable Integer id){
        return jointLeaveService.getById(id);
    }

    @PostMapping
    public JointLeave create(@RequestBody JointLeave jointLeave){
        return jointLeaveService.create(jointLeave);
    }

    @PutMapping("/update/{id}")
    public JointLeave update(@PathVariable Integer id, @RequestBody JointLeave jointLeave){
        return jointLeaveService.update(id,jointLeave);
    }

    @DeleteMapping("/{id}")
    public JointLeave delete(@PathVariable Integer id){
        return jointLeaveService.delete(id);
    }
}
