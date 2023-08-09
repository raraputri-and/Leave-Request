package id.co.mii.serverapp.controllers;

import id.co.mii.serverapp.models.Employee;
//import id.co.mii.serverapp.models.dto.request.EmployeeRequest;
//
//import org.springframework.security.access.prepost.PreAuthorize;
import id.co.mii.serverapp.models.User;
import id.co.mii.serverapp.models.dto.request.EmployeeRequest;
import id.co.mii.serverapp.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import id.co.mii.serverapp.services.EmployeeService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/emp")
@PreAuthorize("hasAnyRole('Admin', 'Employee', 'Manager')")
public class EmployeeController {

    private EmployeeService employeeService;
    @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_EMPLOYEE', 'READ_MANAGER')")
    @GetMapping
    public List<Employee> getAll(){
        return employeeService.getAll();
    }
    @PreAuthorize("hasAnyAuthority('READ_ADMIN','READ_EMPLOYEE', 'READ_MANAGER')")
    @GetMapping("/{id}")
    public Employee getById(@PathVariable Integer id){
        return employeeService.getById(id);
    }

    @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
    @PutMapping("/{id}")
    public Employee update(@PathVariable Integer id, @RequestBody EmployeeRequest employeeRequest) {
        return employeeService.update(id, employeeRequest);
    }


}
