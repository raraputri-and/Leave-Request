package id.co.mii.serverapp.services;

import id.co.mii.serverapp.models.LeaveRequest;
import id.co.mii.serverapp.models.LeaveRequestStatus;
import id.co.mii.serverapp.models.dto.request.LeaveRequestRequest;
import id.co.mii.serverapp.models.dto.request.LeaveRequestStatusRequest;
import id.co.mii.serverapp.repositories.LeaveRequestStatusRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class LeaveRequestStatusService {
    private LeaveRequestStatusRepository leaveRequestStatusRepository;
    private ModelMapper modelMapper;
    private EmployeeService employeeService;
    private StatusActionService statusActionService;
    private LeaveRequestService leaveRequestService;

    public List<LeaveRequestStatus> getAll() {
        return leaveRequestStatusRepository.findAll();
    }

    public LeaveRequestStatus getById(Integer id) {
        return leaveRequestStatusRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found!!"));
    }

}
