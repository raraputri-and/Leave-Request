package id.co.mii.clientapp.controllers.restcontroller;

import id.co.mii.clientapp.models.Employee;
import id.co.mii.clientapp.models.dto.EmployeeRequest;
import id.co.mii.clientapp.models.dto.EmployeeResponse;
import id.co.mii.clientapp.services.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emp")
@AllArgsConstructor
public class RestEmployeeController {
    private EmployeeService employeeService;

    @GetMapping
    public List<EmployeeResponse> getAll(){
        return employeeService.getAll();
    }

    @GetMapping("/{id}")
    public EmployeeResponse getById(@PathVariable Integer id){
        return employeeService.getById(id);
    }

    @PostMapping
    public Employee create(@RequestBody EmployeeRequest employeeRequest){
        return employeeService.create(employeeRequest);
    }

    @PutMapping("/update/{id}")
    public Employee update(@PathVariable Integer id, @RequestBody EmployeeRequest employeeRequest){
        return employeeService.update(id,employeeRequest);
    }
}
