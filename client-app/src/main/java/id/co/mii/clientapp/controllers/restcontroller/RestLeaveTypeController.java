package id.co.mii.clientapp.controllers.restcontroller;

import id.co.mii.clientapp.models.LeaveType;
import id.co.mii.clientapp.services.LeaveTypeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/leave-type")
public class RestLeaveTypeController {
    private LeaveTypeService leaveTypeService;

    @GetMapping
    public List<LeaveType> getAll(){
        return leaveTypeService.getAll();
    }

    @GetMapping("/{id}")
    public LeaveType getById(@PathVariable Integer id){
        return leaveTypeService.getById(id);
    }
}
