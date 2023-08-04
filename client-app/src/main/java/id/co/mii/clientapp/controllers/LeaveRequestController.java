package id.co.mii.clientapp.controllers;

import id.co.mii.clientapp.models.dto.LeaveRequestRequest;
import id.co.mii.clientapp.services.EmployeeService;
import id.co.mii.clientapp.services.LeaveRequestService;
import id.co.mii.clientapp.services.LeaveTypeService;
import id.co.mii.clientapp.services.StatusActionService;
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
        model.addAttribute("statusAction", statusActionService.getAll());
        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("title", "leaveRequest");
        return "Employee/form";
    }

    @GetMapping("/action")
    public String action(Model model, LeaveRequestRequest leaveRequestRequest){
        model.addAttribute("leaveRequests", leaveRequestService.getByStatusAction());
        model.addAttribute("leaveType", leaveTypeService.getAll());
        model.addAttribute("statusAction", statusActionService.getAll());
        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("title", "leaveRequest");
        return "Manager/LeaveRequest";
    }

    @PostMapping
    public String create(LeaveRequestRequest leaveRequestRequest){
        leaveRequestService.create(leaveRequestRequest);
        return "redirect:/tracking";
    }

    @PutMapping("/accept/{id}")
    public String accept(@PathVariable Integer id, LeaveRequestRequest leaveRequestRequest){
        leaveRequestService.accept(id, leaveRequestRequest);
        return "redirect:/tracking";
    }

    @PutMapping("/reject/{id}")
    public String reject(@PathVariable Integer id, LeaveRequestRequest leaveRequestRequest){
        leaveRequestService.reject(id, leaveRequestRequest);
        return "redirect:/tracking";
    }
}
