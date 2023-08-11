package id.co.mii.clientapp.controllers.restcontroller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.co.mii.clientapp.models.LeaveRequestStatus;
import id.co.mii.clientapp.services.LeaveRequestStatusService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/leave-request-status")

public class RestLeaveRequestStatusController {
    private LeaveRequestStatusService leaveRequestStatusService;

    // @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping
    public List<LeaveRequestStatus> getAll() {
        return leaveRequestStatusService.getAll();
    }

    // @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping("/{id}")
    public LeaveRequestStatus getById(@PathVariable Integer id) {
        return leaveRequestStatusService.getById(id);
    }

    @GetMapping("/tracking")
    public List<LeaveRequestStatus> getByCurrentUser() {
        return leaveRequestStatusService.getByCurrentUser();
    }

    @GetMapping("/get-leave-request/{id}")
    public List<LeaveRequestStatus> getByLeaveRequest(@PathVariable Integer id) {
        return leaveRequestStatusService.getByLeaveRequest(id);
    }

}
