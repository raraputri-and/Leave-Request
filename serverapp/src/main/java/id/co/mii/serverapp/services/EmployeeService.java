package id.co.mii.serverapp.services;

import id.co.mii.serverapp.models.Gender;
import id.co.mii.serverapp.models.Role;
import id.co.mii.serverapp.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import id.co.mii.serverapp.models.Employee;
import id.co.mii.serverapp.models.dto.request.EmployeeRequest;

import java.util.ArrayList;
import java.util.List;
import id.co.mii.serverapp.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeService {

    private EmployeeRepository employeeRepository;
//    private UserRepository userRepository;
    private ModelMapper modelMapper;
//    private UserService userService;
    private RoleService roleService;


    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Employee getById(Integer id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found!!"));
    }

//    public Employee create(EmployeeRequest employeeRequest) {
//           Employee employee = modelMapper.map(employeeRequest, Employee.class);
//           User user = modelMapper.map(employeeRequest, User.class);
////         set default role
//           List<Role> roles = new ArrayList<>();
//           roles.add(roleService.getById(1));
//           user.setRoles(roles);
//
//           employee.setUser(user);
//           user.setEmployee(employee);
//           return employeeRepository.save(employee);
//    }

    public Employee update(Integer id, EmployeeRequest employeeRequest) {
        Employee existingEmployee = getById(id);
        existingEmployee.setId(id);
        existingEmployee.setNip(employeeRequest.getNip());
        existingEmployee.setName(employeeRequest.getName());
        existingEmployee.setGender(Gender.valueOf(employeeRequest.getGender()));
        existingEmployee.getUser().setUsername(employeeRequest.getUsername());
        existingEmployee.getUser().setPassword(employeeRequest.getPassword());

        return employeeRepository.save(existingEmployee);
    }

    public Employee delete(Integer id) {
        Employee employee = getById(id);
        employeeRepository.delete(employee);
        return employee;
    }

}
