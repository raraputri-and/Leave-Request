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
    
    @GetMapping("/manager")
    public String tracking(Model model){
        model.addAttribute("leaveRequestManager", leaveRequestService.managerAction());
        model.addAttribute("title", "tracking");
        return "Manager/tracking";
    }

}
