package id.co.mii.clientapp.controllers;

import id.co.mii.clientapp.models.dto.EmployeeRequest;
import id.co.mii.clientapp.services.EmployeeService;
import id.co.mii.clientapp.services.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/emp")
public class EmployeeController {
    private EmployeeService employeeService;
    private RegistrationService registrationService;

//    @PreAuthorize("hasAnyAuthority('READ_ADMIN','READ_USER')")
    @GetMapping
    public String index(Model model){
        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("title","employee");
        return "Admin/employee";
    }

//    @PreAuthorize("hasAnyAuthority('CREATE_ADMIN','CREATE_USER')")
    @PostMapping
    public String create(EmployeeRequest employeeRequest){
        registrationService.registration(employeeRequest);
        return "redirect:/emp";
    }
//    @PreAuthorize("hasAnyAuthority('READ_ADMIN','READ_USER')")
    @GetMapping("/update/{id}")
    public String updateView(@PathVariable Integer id, Model model){
        model.addAttribute("employee", employeeService.getById(id));
        model.addAttribute("user", employeeService.getAll());
        return "Admin/employee";
    }
//    @PreAuthorize("hasAnyAuthority('UPDATE_ADMIN','UPDATE_USER')")
    @PutMapping("/{id}")
    public String update(@PathVariable Integer id, EmployeeRequest employeeRequest){
        employeeService.update(id, employeeRequest);
        return "redirect:/emp";
    }

}
