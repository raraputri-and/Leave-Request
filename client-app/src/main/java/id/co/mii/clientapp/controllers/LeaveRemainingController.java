package id.co.mii.clientapp.controllers;

import id.co.mii.clientapp.models.dto.LeaveRemainingRequest;
import id.co.mii.clientapp.services.EmployeeService;
import id.co.mii.clientapp.services.LeaveRemainingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/leave-remaining")
public class LeaveRemainingController {
    private LeaveRemainingService leaveRemainingService;
    private EmployeeService employeeService;

    @GetMapping
    public String index(Model model){
        model.addAttribute("leaveRemainings", leaveRemainingService.getAll());
        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("title", "leaveRemaining");
        return "Admin/leaveRemaining";
    }

    @PostMapping
    public String create(LeaveRemainingRequest leaveRemainingRequest){
        leaveRemainingService.create(leaveRemainingRequest);
        return "redirect:/joint-leave";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Integer id, LeaveRemainingRequest leaveRemainingRequest){
        leaveRemainingService.update(id,leaveRemainingRequest);
        return "redirect:/joint-leave";
    }
}
