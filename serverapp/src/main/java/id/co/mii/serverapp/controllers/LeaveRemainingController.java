package id.co.mii.serverapp.controllers;

import id.co.mii.serverapp.models.LeaveRemaining;
import id.co.mii.serverapp.models.dto.request.LeaveRemainingRequest;
import id.co.mii.serverapp.services.LeaveRemainingService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/leave-remaining")
@PreAuthorize("hasAnyRole('Admin', 'Employee', 'Manager')")
public class LeaveRemainingController {
    private LeaveRemainingService leaveRemainingService;
    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping
    public List<LeaveRemaining> getAll() {
        return leaveRemainingService.getAll();
    }
    @PreAuthorize("hasAnyAuthority('READ_ADMIN','READ_EMPLOYEE', 'READ_MANAGER')")
    @GetMapping("/{id}")
    public LeaveRemaining getById(@PathVariable Integer id) {
        return leaveRemainingService.getById(id);
    }

//    @PostMapping
//    public LeaveRemaining create(@RequestBody LeaveRemainingRequest leaveRemainingRequest) {
//        return leaveRemainingService.create(leaveRemainingRequest);
//    }
    @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
    @PutMapping("/{id}")
    public LeaveRemaining update(@PathVariable Integer id, @RequestBody LeaveRemainingRequest leaveRemainingRequest) {
        return leaveRemainingService.update(id, leaveRemainingRequest);
    }

}
