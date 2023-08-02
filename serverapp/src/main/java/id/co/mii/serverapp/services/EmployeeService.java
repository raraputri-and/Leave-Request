package id.co.mii.serverapp.services;

import id.co.mii.serverapp.models.Gender;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import id.co.mii.serverapp.models.Employee;
import id.co.mii.serverapp.models.dto.request.EmployeeRequest;


import java.util.List;
import id.co.mii.serverapp.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private PasswordEncoder passwordEncoder;


    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Employee getById(Integer id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found!!"));
    }

    public Employee update(Integer id, EmployeeRequest employeeRequest) {
        Employee existingEmployee = getById(id);
        existingEmployee.setId(id);
        existingEmployee.setNip(employeeRequest.getNip());
        existingEmployee.setName(employeeRequest.getName());
        existingEmployee.setGender(Gender.valueOf(employeeRequest.getGender()));
        existingEmployee.setReligion(employeeRepository.findById(employeeRequest.getReligionId()).get().getReligion());
        existingEmployee.setManager(employeeRepository.findById(employeeRequest.getManagerId()).get());
        existingEmployee.getUser().setUsername(employeeRequest.getUsername());
        existingEmployee.getUser().setPassword(passwordEncoder.encode(employeeRequest.getPassword()));

        return employeeRepository.save(existingEmployee);
    }


}
