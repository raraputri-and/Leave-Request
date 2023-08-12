package id.co.mii.clientapp.controllers;

import id.co.mii.clientapp.models.dto.LeaveRequestRequest;
import id.co.mii.clientapp.models.dto.LeaveRequestStatusRequest;
import id.co.mii.clientapp.services.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

@Controller
@AllArgsConstructor
@RequestMapping("/leave-request")
public class LeaveRequestController {
    private LeaveRequestService leaveRequestService;
    private LeaveTypeService leaveTypeService;
    private StatusActionService statusActionService;
    private EmployeeService employeeService;

    @GetMapping
    public String index(@RequestParam(value = "message", required = false, defaultValue = "") String message,
            Model model, LeaveRequestRequest leaveRequestRequest) {
        model.addAttribute("leaveRequests", leaveRequestService.getAll());
        model.addAttribute("leaveType", leaveTypeService.getAll());
        model.addAttribute("employees", employeeService.getAll());
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
    public String create(LeaveRequestRequest leaveRequestRequest) {
        try {
            leaveRequestService.create(leaveRequestRequest);
            return "redirect:/leave-request-status/tracking";
        } catch (HttpClientErrorException e) {
            // if ( e.getStatusCode().toString() == 409) {
            // // code untuk menampilkan pesan "Yang dikirimkan secara deskriptif dari sisi
            // // back end"
            // } else { // untuk handling code 500 dan lainnya
            // // code untuk menampilkan pesan "Sistem mengalami kendala, mohon coba kembali
            // // secara berkala"
            // }
            // System.out.println(e.getStatusCode());
            // System.out.println(e.getResponseBodyAsString());
            String[] convertedJson = e.getResponseBodyAsString().split("\"");
            // int size = convertedJson.length;
            // for (String s : convertedJson) {
            // System.out.println(s);
            // }
            return "redirect:/leave-request?message=" + convertedJson[19];
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
