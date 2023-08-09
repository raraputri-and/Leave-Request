package id.co.mii.clientapp.controllers;

import id.co.mii.clientapp.models.dto.LeaveRequestRequest;
import id.co.mii.clientapp.models.dto.LeaveRequestStatusRequest;
import id.co.mii.clientapp.services.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/leave-request")
public class LeaveRequestController {
    private LeaveRequestService leaveRequestService;
    private LeaveTypeService leaveTypeService;
    private StatusActionService statusActionService;
    private EmployeeService employeeService;

    @GetMapping
    public String index(Model model, LeaveRequestRequest leaveRequestRequest){
        model.addAttribute("leaveRequests", leaveRequestService.getAll());
        model.addAttribute("leaveType", leaveTypeService.getAll());
        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("title", "leaveRequest");
        return "Employee/form";
    }

    @GetMapping("/action")
    public String action(Model model, LeaveRequestRequest leaveRequestRequest){
        model.addAttribute("leaveRequests", leaveRequestService.getByStatusAction());
        model.addAttribute("title", "action");
        return "Manager/ApproveLeaveRequest";
    }

//    @PreAuthorize("hasAnyAuthority('READ_ADMIN','READ_USER')")
    @GetMapping("/reject/{id}")
    public String rejectView(@PathVariable Integer id,Model model,LeaveRequestStatusRequest leaveRequestStatusRequest){
        model.addAttribute("id", id);
        return "Manager/RejectForm";
    }

    @PostMapping
    public String create(LeaveRequestRequest leaveRequestRequest){
        leaveRequestService.create(leaveRequestRequest);
        return "redirect:/leave-request-status/tracking";
    }

    @PutMapping("/accept/{id}")
    public String accept(@PathVariable Integer id){
        leaveRequestService.accept(id);
        return "redirect:/tracking/manager";
    }

    @PutMapping("/reject/{id}")
    public String reject(@PathVariable Integer id, LeaveRequestStatusRequest leaveRequestStatusRequest){
        leaveRequestService.reject(id, leaveRequestStatusRequest);
        return "redirect:/tracking/manager";
    }
}
