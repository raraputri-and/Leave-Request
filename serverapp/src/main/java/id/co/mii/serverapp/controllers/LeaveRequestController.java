package id.co.mii.serverapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.co.mii.serverapp.models.LeaveRequest;
import id.co.mii.serverapp.models.dto.request.LeaveRequestRequest;
import id.co.mii.serverapp.services.LeaveRequestService;

@RestController
@RequestMapping("/leave-requests")
public class LeaveRequestController {
    private final LeaveRequestService leaveRequestService;

    @Autowired
    public LeaveRequestController(LeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
    }

    @PostMapping
    public ResponseEntity<LeaveRequest> createLeaveRequest(@RequestBody LeaveRequestRequest leaveRequestRequest) {
        try {
            LeaveRequest savedLeaveRequest = leaveRequestService.createLeaveRequest(leaveRequestRequest);
            // You can add additional headers or status codes as needed in the
            // ResponseEntity
            return ResponseEntity.ok(savedLeaveRequest);
        } catch (IllegalArgumentException e) {
            // Optionally, handle the case where invalid data is submitted
            // You can return a specific error message or status code
            return ResponseEntity.badRequest().body(null);
        }
    }

    // ...
}