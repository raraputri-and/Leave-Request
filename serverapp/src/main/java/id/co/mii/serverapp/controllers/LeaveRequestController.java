package id.co.mii.serverapp.controllers;

import id.co.mii.serverapp.models.LeaveRequest;
import id.co.mii.serverapp.models.dto.request.LeaveRequestStatusRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import id.co.mii.serverapp.models.dto.request.LeaveRequestRequest;
import id.co.mii.serverapp.services.LeaveRequestService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/leave-request")
@PreAuthorize("hasAnyRole('Employee', 'Manager')")
public class LeaveRequestController {
    private LeaveRequestService leaveRequestService;
    @PreAuthorize("hasAnyAuthority('READ_MANAGER','READ_EMPLOYEE')")
    @GetMapping
    public List<LeaveRequest> getAll() {
        return leaveRequestService.getAll();
    }
    @PreAuthorize("hasAnyAuthority('READ_MANAGER', 'READ_EMPLOYEE')")
    @GetMapping("/{id}")
    public LeaveRequest getById(@PathVariable Integer id) {
        return leaveRequestService.getById(id);
    }
    @PreAuthorize("hasAuthority('READ_MANAGER')")
    @GetMapping("/action")
    public List<LeaveRequest> getByStatusAction(){
        return leaveRequestService.getByStatus();
    }
    @PreAuthorize("hasAuthority('READ_MANAGER')")
    @GetMapping("/manager-tracking")
    public List<LeaveRequest> managerTracking(){
        return leaveRequestService.managerTracking();
    }
    @PreAuthorize("hasAnyAuthority('READ_EMPLOYEE','READ_MANAGER')")
    @GetMapping("/user-tracking")
    public List<LeaveRequest> getByCurrentUser(){
        return leaveRequestService.getByCurrentUser();
    }
    @PreAuthorize("hasAnyAuthority('CREATE_EMPLOYEE', 'CREATE_MANAGER')")
    @PostMapping
    public LeaveRequest create(LeaveRequestRequest leaveRequestRequest) {
        return leaveRequestService.create(leaveRequestRequest);
    }
    @PreAuthorize("hasAuthority('UPDATE_MANAGER')")
    @PutMapping("accept/{id}")
    public LeaveRequest accept(@PathVariable Integer id, @RequestBody LeaveRequestStatusRequest leaveRequestStatusRequest) {
        return leaveRequestService.accept(id, leaveRequestStatusRequest);
    }
    @PreAuthorize("hasAuthority('UPDATE_MANAGER')")
    @PutMapping("reject/{id}")
    public LeaveRequest reject(@PathVariable Integer id, @RequestBody LeaveRequestStatusRequest leaveRequestStatusRequest) {
        return leaveRequestService.reject(id, leaveRequestStatusRequest);
    }
    @PreAuthorize("hasAnyAuthority('READ_MANAGER', 'READ_EMPLOYEE')")
    @GetMapping("/attachment/{id}")
    public ResponseEntity<?> showAttachment(@PathVariable Integer id){
        byte[] imageData=leaveRequestService.showAttachment(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }
}