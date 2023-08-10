package id.co.mii.serverapp.services;

import id.co.mii.serverapp.models.LeaveRequestStatus;
import id.co.mii.serverapp.models.User;
import id.co.mii.serverapp.repositories.LeaveRequestStatusRepository;
import lombok.AllArgsConstructor;
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
    private LeaveRequestService leaveRequestService;

    public List<LeaveRequestStatus> getAll() {
        return leaveRequestStatusRepository.findAll();
    }

    public LeaveRequestStatus getById(Integer id) {
        return leaveRequestStatusRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Leave Request Status not found!!"));
    }

    public List<LeaveRequestStatus> getByCurrentUser() {
        User user = userService.getCurrentUser();
        return leaveRequestStatusRepository.findAll()
                .stream()
                .filter(lrs -> Objects.equals(lrs.getLeaveRequest().getEmployee().getId(), user.getId()))
                .collect(Collectors.toList());
    }

    public List<LeaveRequestStatus> getByLeaveRequest(Integer id) {
        return leaveRequestStatusRepository.findAll()
                .stream()
                .filter(lrs -> Objects.equals(lrs.getLeaveRequest().getId(), leaveRequestService.getById(id).getId()))
                .collect(Collectors.toList());
    }
}
