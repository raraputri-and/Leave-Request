package id.co.mii.serverapp.services;

import id.co.mii.serverapp.models.Employee;
import id.co.mii.serverapp.models.Gender;
import id.co.mii.serverapp.models.LeaveRemaining;
import id.co.mii.serverapp.models.LeaveRequestStatus;
import id.co.mii.serverapp.models.User;
import id.co.mii.serverapp.models.dto.request.LeaveRemainingRequest;
import id.co.mii.serverapp.models.dto.request.LeaveRemainingRequest;
import id.co.mii.serverapp.repositories.EmployeeRepository;
import id.co.mii.serverapp.repositories.LeaveRemainingRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LeaveRemainingService {
    private LeaveRemainingRepository leaveRemainingRepository;
    private ModelMapper modelMapper;
    private EmployeeService employeeService;
    private EmployeeRepository employeeRepository;
    private UserService userService;
    
    public List<LeaveRemaining> getAll() {
        return leaveRemainingRepository.findAll();
    }

    public LeaveRemaining getById(Integer id) {
        return leaveRemainingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found!!"));
    }

    public LeaveRemaining getByCurrentUser(Integer id) {
        User user = userService.getCurrentUser();
        LeaveRemaining leaveRemaining = getById(user.getId());
        return leaveRemaining;
    }
    

//    public LeaveRemaining create(LeaveRemainingRequest leaveRemainingRequest) {
//
//        LeaveRemaining leaveRemaining = modelMapper.map(leaveRemainingRequest, LeaveRemaining.class);
//
//        return leaveRemainingRepository.save(leaveRemaining);
//    }

    public LeaveRemaining update(Integer id, LeaveRemainingRequest leaveRemainingRequest) {
        LeaveRemaining existingLeaveRemaining = getById(id);
        existingLeaveRemaining.setId(id);
        existingLeaveRemaining.setPastRemaining(leaveRemainingRequest.getPastRemaining());
        existingLeaveRemaining.setPresentRemaining(leaveRemainingRequest.getPresentRemaining());

        return leaveRemainingRepository.save(existingLeaveRemaining);
    }
}
