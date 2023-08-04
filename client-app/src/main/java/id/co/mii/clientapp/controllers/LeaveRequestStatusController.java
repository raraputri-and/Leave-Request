package id.co.mii.clientapp.controllers;

import id.co.mii.clientapp.models.dto.EmployeeRequest;
import id.co.mii.clientapp.models.dto.LeaveRequestStatusRequest;
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
    //    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping
    public String index(Model model){
        model.addAttribute("leaveRequestStatuses", leaveRequestStatusService.getAll());
        model.addAttribute("title","leaveRequestStatus");
        return "Employee/tracking";
    }

    //    @PreAuthorize("hasAnyAuthority('CREATE_ADMIN','CREATE_USER')")
//    @PostMapping("/accept")
//    public String createAccept(LeaveRequestStatusRequest leaveRequestStatusRequest){
//        leaveRequestStatusService.createAccept(leaveRequestStatusRequest);
//        return "redirect:/tracking";
//    }
}
