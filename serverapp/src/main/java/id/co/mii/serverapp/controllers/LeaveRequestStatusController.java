package id.co.mii.serverapp.controllers;

import id.co.mii.serverapp.models.LeaveRequestStatus;
import id.co.mii.serverapp.models.dto.request.LeaveRequestStatusRequest;
import id.co.mii.serverapp.services.LeaveRequestStatusService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/leave-request-status")

public class LeaveRequestStatusController {
    private LeaveRequestStatusService leaveRequestStatusService;
    //    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping
    public List<LeaveRequestStatus> getAll() {
        return leaveRequestStatusService.getAll();
    }
    //    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping("/{id}")
    public LeaveRequestStatus getById(@PathVariable Integer id) {
        return leaveRequestStatusService.getById(id);
    }

    @GetMapping("/tracking")
    public List<LeaveRequestStatus> getByCurrentUser(){
        return leaveRequestStatusService.getByCurrentUser();
    }

}
