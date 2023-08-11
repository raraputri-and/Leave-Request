package id.co.mii.clientapp.controllers.restcontroller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.co.mii.clientapp.models.LeaveRequest;
import id.co.mii.clientapp.models.dto.LeaveRequestRequest;
import id.co.mii.clientapp.models.dto.LeaveRequestStatusRequest;
import id.co.mii.clientapp.services.LeaveRequestService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/leave-request")
public class RestLeaveRequestController {
    private LeaveRequestService leaveRequestService;

    // @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping
    public List<LeaveRequest> getAll() {
        return leaveRequestService.getAll();
    }

    // @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping("/{id}")
    public LeaveRequest getById(@PathVariable Integer id) {
        return leaveRequestService.getById(id);
    }

    @GetMapping("/action")
    public List<LeaveRequest> getByStatusAction() {
        return leaveRequestService.getByStatusAction();
    }

    @GetMapping("/manager-tracking")
    public List<LeaveRequest> managerTracking() {
        return leaveRequestService.managerAction();
    }

    @GetMapping("/user-tracking")
    public List<LeaveRequest> getByCurrentUser() {
        return leaveRequestService.getByCurrentUser();
    }

    // @PreAuthorize("hasAuthority('CREATE_ADMIN')")
    @PostMapping
    public LeaveRequest create(@RequestBody LeaveRequestRequest leaveRequestRequest) {
        return leaveRequestService.create(leaveRequestRequest);
    }

    @PutMapping("/accept/{id}")
    public LeaveRequest accept(@PathVariable Integer id,
            @RequestBody LeaveRequestStatusRequest leaveRequestStatusRequest) {
        return leaveRequestService.accept(id, leaveRequestStatusRequest);
    }

    @PutMapping("/reject/{id}")
    public LeaveRequest reject(@PathVariable Integer id,
            @RequestBody LeaveRequestStatusRequest leaveRequestStatusRequest) {
        return leaveRequestService.reject(id, leaveRequestStatusRequest);
    }
}