package id.co.mii.serverapp.controllers;

import id.co.mii.serverapp.models.LeaveRequest;
import id.co.mii.serverapp.models.dto.request.LeaveRequestStatusRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import id.co.mii.serverapp.models.dto.request.LeaveRequestRequest;
import id.co.mii.serverapp.services.LeaveRequestService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/leave-request")
public class LeaveRequestController {
    private LeaveRequestService leaveRequestService;
    //    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping
    public List<LeaveRequest> getAll() {
        return leaveRequestService.getAll();
    }
    //    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping("/{id}")
    public LeaveRequest getById(@PathVariable Integer id) {
        return leaveRequestService.getById(id);
    }

    @GetMapping("/action")
    public List<LeaveRequest> getByStatusAction(){
        return leaveRequestService.getByStatus();
    }

    @GetMapping("/manager-tracking")
    public List<LeaveRequest> managerTracking(){
        return leaveRequestService.managerTracking();
    }
    //    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
    @PostMapping
    public LeaveRequest create(@RequestBody LeaveRequestRequest leaveRequestRequest) {
        return leaveRequestService.create(leaveRequestRequest);
    }

    @PutMapping("accept/{id}")
    public LeaveRequest accept(@PathVariable Integer id) {
        return leaveRequestService.accept(id);
    }

    @PutMapping("reject/{id}")
    public LeaveRequest reject(@PathVariable Integer id, @RequestBody LeaveRequestStatusRequest leaveRequestStatusRequest) {
        return leaveRequestService.reject(id, leaveRequestStatusRequest);
    }
}