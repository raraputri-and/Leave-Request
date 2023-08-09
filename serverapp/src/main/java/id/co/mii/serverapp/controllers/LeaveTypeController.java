package id.co.mii.serverapp.controllers;

import id.co.mii.serverapp.models.LeaveType;
import id.co.mii.serverapp.services.LeaveTypeService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/leave-type")
@PreAuthorize("hasAnyRole('Employee','Manager')")
public class LeaveTypeController {
    private LeaveTypeService leaveTypeService;
    @PreAuthorize("hasAnyAuthority('READ_EMPLOYEE', 'READ_MANAGER')")
    @GetMapping
    public List<LeaveType> getAll() {
        return leaveTypeService.getAll();
    }
    @PreAuthorize("hasAnyAuthority('READ_EMPLOYEE','READ_MANAGER')")
    @GetMapping("/{id}")
    public LeaveType getById(@PathVariable Integer id) {
        return leaveTypeService.getById(id);
    }
}
