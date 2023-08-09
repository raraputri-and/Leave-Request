package id.co.mii.serverapp.controllers;

import id.co.mii.serverapp.models.LeaveRequestStatus;
import id.co.mii.serverapp.models.dto.request.LeaveRequestStatusRequest;
import id.co.mii.serverapp.services.LeaveRequestStatusService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/leave-request-status")
@PreAuthorize("hasAnyRole('Employee','Manager')")
public class LeaveRequestStatusController {
    private LeaveRequestStatusService leaveRequestStatusService;
    @PreAuthorize("hasAnyAuthority('READ_EMPLOYEE','READ_MANAGER')")
    @GetMapping
    public List<LeaveRequestStatus> getAll() {
        return leaveRequestStatusService.getAll();
    }
    @PreAuthorize("hasAnyAuthority('READ_EMPLOYEE','READ_MANAGER')")
    @GetMapping("/{id}")
    public LeaveRequestStatus getById(@PathVariable Integer id) {
        return leaveRequestStatusService.getById(id);
    }
    @PreAuthorize("hasAnyAuthority('READ_EMPLOYEE','READ_MANAGER')")
    @GetMapping("/tracking")
    public List<LeaveRequestStatus> getByCurrentUser(){
        return leaveRequestStatusService.getByCurrentUser();
    }

}
