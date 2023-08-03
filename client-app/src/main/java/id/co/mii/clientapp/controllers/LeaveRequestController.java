package id.co.mii.clientapp.controllers;

import id.co.mii.clientapp.models.LeaveRequest;
import id.co.mii.clientapp.models.LeaveType;
import id.co.mii.clientapp.models.dto.LeaveRequestRequest;
import id.co.mii.clientapp.services.EmployeeService;
import id.co.mii.clientapp.services.LeaveRequestService;
import id.co.mii.clientapp.services.LeaveTypeService;
import id.co.mii.clientapp.services.StatusActionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;

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
        model.addAttribute("leaveType", leaveTypeService.getAll());
        model.addAttribute("statusAction", statusActionService.getAll());
        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("title", "leaveRequest");
        return "Employee/leaveRequest/index";
    }

    @PostMapping
    public String create(LeaveRequestRequest leaveRequestRequest){
        leaveRequestService.create(leaveRequestRequest);
        return "redirect:/leave-request";
    }
}
