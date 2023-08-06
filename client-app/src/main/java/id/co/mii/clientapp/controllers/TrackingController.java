package id.co.mii.clientapp.controllers;

import id.co.mii.clientapp.services.EmployeeService;
import id.co.mii.clientapp.services.LeaveRequestService;
import id.co.mii.clientapp.services.LeaveTypeService;
import id.co.mii.clientapp.services.StatusActionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/tracking")
public class TrackingController {
    private LeaveRequestService leaveRequestService;
    private LeaveTypeService leaveTypeService;
    private StatusActionService statusActionService;
    private EmployeeService employeeService;
    
    @GetMapping
    public String tracking(Model model){
        model.addAttribute("leaveRequests", leaveRequestService.getAll());
        model.addAttribute("leaveType", leaveTypeService.getAll());
        model.addAttribute("statusAction", statusActionService.getAll());
        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("title", "tracking");
        return "Employee/tracking";
    }
}
