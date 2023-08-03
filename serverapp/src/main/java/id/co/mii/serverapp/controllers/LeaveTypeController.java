package id.co.mii.serverapp.controllers;

import id.co.mii.serverapp.models.LeaveType;
import id.co.mii.serverapp.services.LeaveTypeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/leave-type")
public class LeaveTypeController {
    private LeaveTypeService leaveTypeService;
    //    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping
    public List<LeaveType> getAll() {
        return leaveTypeService.getAll();
    }
    //    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping("/{id}")
    public LeaveType getById(@PathVariable Integer id) {
        return leaveTypeService.getById(id);
    }
}
