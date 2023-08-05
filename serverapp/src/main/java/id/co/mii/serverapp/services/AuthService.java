package id.co.mii.serverapp.services;

import id.co.mii.serverapp.models.Employee;
import id.co.mii.serverapp.models.LeaveRemaining;
import id.co.mii.serverapp.models.Role;
import id.co.mii.serverapp.models.User;
import id.co.mii.serverapp.models.dto.request.EmployeeRequest;
import id.co.mii.serverapp.models.dto.request.LoginRequest;
import id.co.mii.serverapp.models.dto.response.LoginResponse;
import id.co.mii.serverapp.repositories.EmployeeRepository;
import id.co.mii.serverapp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthService {
    private EmployeeRepository employeeRepository;
    private ModelMapper modelMapper;
    private RoleService roleService;
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private AppUserDetailService appUserDetailService;
    private PasswordEncoder passwordEncoder;
    private ReligionService religionService;

    @SneakyThrows
    public Employee registration(EmployeeRequest employeeRequest) {

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
        leaveRemaining.setPresentRemaining(12);

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

    public LoginResponse login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authReq);
        SecurityContextHolder.getContext().setAuthentication(auth);
        User user = userRepository.findByUsername(
                loginRequest.getUsername()).get();
        UserDetails userDetails = appUserDetailService.loadUserByUsername(
                loginRequest.getUsername());

        List<String> authorities = userDetails.getAuthorities().stream().map(authority -> authority.getAuthority())
                .collect(Collectors.toList());
        return new LoginResponse(user.getUsername(), authorities);

    }
}
