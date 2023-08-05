package id.co.mii.serverapp.controllers;

import id.co.mii.serverapp.models.Employee;
//import id.co.mii.serverapp.models.dto.request.EmployeeRequest;
//
//import org.springframework.security.access.prepost.PreAuthorize;
import id.co.mii.serverapp.models.User;
import id.co.mii.serverapp.models.dto.request.EmployeeRequest;
import id.co.mii.serverapp.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import id.co.mii.serverapp.services.EmployeeService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/emp")
//@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class EmployeeController {

    private EmployeeService employeeService;
    private UserService userService;
//    @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_USER')")
    @GetMapping
    public List<Employee> getAll(){
        return employeeService.getAll();
    }
//    @PreAuthorize("hasAnyAuthority('READ_ADMIN','READ_USER')")
    @GetMapping("/{id}")
    public Employee getById(@PathVariable Integer id){
        return employeeService.getById(id);
    }

//    @PreAuthorize("hasAnyAuthority('UPDATE_ADMIN','UPDATE_USER')")
    @PutMapping("/{id}")
    public Employee update(@PathVariable Integer id, @RequestBody EmployeeRequest employeeRequest) {
        return employeeService.update(id, employeeRequest);
    }


}
