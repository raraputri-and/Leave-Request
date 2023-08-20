package id.co.mii.serverapp.controllers;

import id.co.mii.serverapp.models.Employee;
//import id.co.mii.serverapp.models.dto.request.EmployeeRequest;
//
//import org.springframework.security.access.prepost.PreAuthorize;
import id.co.mii.serverapp.models.User;
import id.co.mii.serverapp.models.dto.request.EmployeeRequest;
import id.co.mii.serverapp.models.dto.response.EmployeeResponse;
import id.co.mii.serverapp.models.dto.response.ManagerResponse;
import id.co.mii.serverapp.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<EmployeeResponse> getAll(){
        List<EmployeeResponse> employeeResponses = employeeService.getAll().stream().map(emp -> {
            EmployeeResponse employeeResponse = new EmployeeResponse();
            employeeResponse.setId(emp.getId());
            employeeResponse.setNip(emp.getNip());
            employeeResponse.setName(emp.getName());
            employeeResponse.setEmail(emp.getEmail());
            employeeResponse.setJoinDate(emp.getJoinDate());
            employeeResponse.setGender(String.valueOf(emp.getGender()));
            employeeResponse.setReligion(emp.getReligion());
            ManagerResponse managerResponse = new ManagerResponse();
            managerResponse.setId(emp.getManager().getId());
            managerResponse.setName(emp.getManager().getName());
            employeeResponse.setManager(managerResponse);
            employeeResponse.setUsername(emp.getUser().getUsername());
            employeeResponse.setPassword(emp.getUser().getPassword());
            return employeeResponse;
        }).collect(Collectors.toList());
        return employeeResponses;
    }
    @PreAuthorize("hasAnyAuthority('READ_ADMIN','READ_EMPLOYEE', 'READ_MANAGER')")
    @GetMapping("/{id}")
    public EmployeeResponse getById(@PathVariable Integer id){
        Employee emp = employeeService.getById(id);
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setId(emp.getId());
        employeeResponse.setNip(emp.getNip());
        employeeResponse.setName(emp.getName());
        employeeResponse.setEmail(emp.getEmail());
        employeeResponse.setJoinDate(emp.getJoinDate());
        employeeResponse.setGender(String.valueOf(emp.getGender()));
        employeeResponse.setReligion(emp.getReligion());
        ManagerResponse managerResponse = new ManagerResponse();
        managerResponse.setId(emp.getManager().getId());
        managerResponse.setName(emp.getManager().getName());
        employeeResponse.setManager(managerResponse);
        employeeResponse.setUsername(emp.getUser().getUsername());
        employeeResponse.setPassword(emp.getUser().getPassword());
        return employeeResponse;
    }

    @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
    @PutMapping("/{id}")
    public Employee update(@PathVariable Integer id, @RequestBody EmployeeRequest employeeRequest) {
        return employeeService.update(id, employeeRequest);
    }
    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
    @PostMapping
    public Employee create(@RequestBody EmployeeRequest employeeRequest) {
        return employeeService.create(employeeRequest);
    }


}
