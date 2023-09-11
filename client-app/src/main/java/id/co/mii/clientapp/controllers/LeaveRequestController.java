package id.co.mii.clientapp.controllers;

import id.co.mii.clientapp.models.dto.LeaveRequestRequest;
import id.co.mii.clientapp.models.dto.LeaveRequestStatusRequest;
import id.co.mii.clientapp.services.*;
import lombok.AllArgsConstructor;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@AllArgsConstructor
@RequestMapping("/leave-request")
public class LeaveRequestController {
    private LeaveRequestService leaveRequestService;
    private LeaveTypeService leaveTypeService;
    private EmployeeService employeeService;
    private LeaveRemainingService leaveRemainingService;

    @GetMapping
    public String index(@RequestParam(value = "message", required = false, defaultValue = "") String message,
            Model model, LeaveRequestRequest leaveRequestRequest, Integer id) {
        model.addAttribute("leaveRequests", leaveRequestService.getAll());
        model.addAttribute("leaveType", leaveTypeService.getAll());
        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("leaveRemaining", leaveRemainingService.getByCurrentUser(id));
        model.addAttribute("title", "leaveRequest");
        model.addAttribute("message", message);
        return "Employee/form";
    }

    @GetMapping("/action")
    public String action(Model model, LeaveRequestRequest leaveRequestRequest) {
        model.addAttribute("leaveRequests", leaveRequestService.getByStatusAction());
        model.addAttribute("title", "action");
        return "Manager/ApproveLeaveRequest";
    }

    @PostMapping
    public String create(@ModelAttribute LeaveRequestRequest leaveRequestRequest, @RequestParam(name = "attachment")MultipartFile attachment) {
        try {
            leaveRequestService.create(leaveRequestRequest, attachment);
            return "redirect:/leave-request-status/tracking";
        } catch (HttpClientErrorException e) {
            System.out.println(e.getMessage());
            String[] convertedJson = e.getResponseBodyAsString().split("\"");
            return "redirect:/leave-request?message=" + convertedJson[19];
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping("/accept/{id}")
    public String accept(@PathVariable Integer id, LeaveRequestStatusRequest leaveRequestStatusRequest) {
        leaveRequestService.accept(id, leaveRequestStatusRequest);
        return "redirect:/tracking/manager";
    }

    @PutMapping("/reject/{id}")
    public String reject(@PathVariable Integer id, LeaveRequestStatusRequest leaveRequestStatusRequest) {
        leaveRequestService.reject(id, leaveRequestStatusRequest);
        return "redirect:/tracking/manager";
    }

}
