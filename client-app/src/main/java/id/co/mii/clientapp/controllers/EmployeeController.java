package id.co.mii.clientapp.controllers;

import id.co.mii.clientapp.models.dto.EmployeeRequest;
import id.co.mii.clientapp.services.EmployeeService;
import id.co.mii.clientapp.services.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/emp")
@PreAuthorize("hasRole('Admin')")
public class EmployeeController {
    private EmployeeService employeeService;
    private RegistrationService registrationService;

    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping
    public String index(Model model){
        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("title","employee");
        return "Admin/employee";
    }

//    @PreAuthorize("hasAnyAuthority('CREATE_ADMIN','CREATE_USER')")
    @PostMapping
    public String create(EmployeeRequest employeeRequest){
        employeeService.create(employeeRequest);
        return "redirect:/emp";
    }

//    @PreAuthorize("hasAnyAuthority('UPDATE_ADMIN','UPDATE_USER')")
    @PutMapping("/{id}")
    public String update(@PathVariable Integer id, EmployeeRequest employeeRequest){
        employeeService.update(id, employeeRequest);
        return "redirect:/emp";
    }

}
