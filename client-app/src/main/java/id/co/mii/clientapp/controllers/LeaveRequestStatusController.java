package id.co.mii.clientapp.controllers;

import id.co.mii.clientapp.services.LeaveRequestService;
import id.co.mii.clientapp.services.LeaveRequestStatusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@AllArgsConstructor
@RequestMapping("/leave-request-status")
public class LeaveRequestStatusController {
    private LeaveRequestStatusService leaveRequestStatusService;
    private LeaveRequestService leaveRequestService;
    //    @PreAuthorize("hasAuthority('READ_ADMIN')")
//    @GetMapping
//    public String index(Model model){
//        model.addAttribute("leaveRequestStatuses", leaveRequestStatusService.getAll());
//        model.addAttribute("title","leaveRequestStatus");
//        return "Employee/tracking";
//    }

    @GetMapping("/tracking")
    public String getByCurrentUser(Model model){
        model.addAttribute("leaveRequests",leaveRequestService.getByCurrentUser());
        model.addAttribute("leaveRequestStatuses", leaveRequestStatusService.getByCurrentUser());
        model.addAttribute("title","leaveRequestStatus");
        return "Employee/tracking";
    }
}
