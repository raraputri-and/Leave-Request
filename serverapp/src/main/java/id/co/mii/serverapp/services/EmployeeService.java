package id.co.mii.serverapp.services;

import id.co.mii.serverapp.models.*;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import id.co.mii.serverapp.models.dto.request.EmployeeRequest;


import java.util.ArrayList;
import java.util.List;
import id.co.mii.serverapp.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private PasswordEncoder passwordEncoder;
    private ReligionService religionService;
    private ModelMapper modelMapper;
    private RoleService roleService;
    private ParameterService parameterService;


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
        existingEmployee.setReligion(religionService.getById(employeeRequest.getReligionId()));
        existingEmployee.setManager(getById(employeeRequest.getManagerId()));
        existingEmployee.getUser().setUsername(employeeRequest.getUsername());
        existingEmployee.getUser().setPassword(passwordEncoder.encode(employeeRequest.getPassword()));

        return employeeRepository.save(existingEmployee);
    }

    @SneakyThrows
    public Employee create(EmployeeRequest employeeRequest) {

        Employee employee = modelMapper.map(employeeRequest, Employee.class);
        User user = modelMapper.map(employeeRequest, User.class);

        // set password BCrypt
        user.setPassword(passwordEncoder.encode(employeeRequest.getPassword()));

        // set default role
        List<Role> roles = new ArrayList<>();
        roles.add(roleService.getById(1));
        user.setRoles(roles);

        //set default leave remaining
        LeaveRemaining leaveRemaining = new LeaveRemaining();
        leaveRemaining.setPastRemaining(0);
        leaveRemaining.setPresentRemaining(Integer.valueOf(parameterService.getById("Max-leave").getLeaveQty()));

        Employee manager = employeeRepository.findById(employeeRequest.getManagerId()).get();
        employee.setReligion(religionService.getById(employeeRequest.getReligionId()));

        employee.setUser(user);
        user.setEmployee(employee);
        employee.setManager(manager);
        employee.setLeaveRemaining(leaveRemaining);
        leaveRemaining.setEmployee(employee);

        employeeRepository.save(employee);
        return employee;
    }


}
