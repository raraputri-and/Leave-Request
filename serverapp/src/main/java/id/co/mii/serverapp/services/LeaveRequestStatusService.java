package id.co.mii.serverapp.services;

import id.co.mii.serverapp.models.LeaveRequest;
import id.co.mii.serverapp.models.LeaveRequestStatus;
import id.co.mii.serverapp.models.User;
import id.co.mii.serverapp.models.dto.request.LeaveRequestRequest;
import id.co.mii.serverapp.models.dto.request.LeaveRequestStatusRequest;
import id.co.mii.serverapp.repositories.LeaveRequestStatusRepository;
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
public class LeaveRequestStatusService {
    private LeaveRequestStatusRepository leaveRequestStatusRepository;
    private UserService userService;

    public List<LeaveRequestStatus> getAll() {
        return leaveRequestStatusRepository.findAll();
    }

    public LeaveRequestStatus getById(Integer id) {
        return leaveRequestStatusRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Leave Request Status not found!!"));
    }

    public List<LeaveRequestStatus> getByCurrentUser() {
        User user = userService.getCurrentUser();
        return leaveRequestStatusRepository.findAll()
                .stream()
                .filter(lrs -> Objects.equals(lrs.getLeaveRequest().getEmployee().getId(), user.getId()))
                .collect(Collectors.toList());
    }

}
