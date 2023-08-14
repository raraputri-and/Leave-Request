package id.co.mii.serverapp.services;

import id.co.mii.serverapp.models.LeaveType;
import id.co.mii.serverapp.repositories.LeaveTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class LeaveTypeService {
    private LeaveTypeRepository leaveTypeRepository;

    public List<LeaveType> getAll() {
        return leaveTypeRepository.findAll();
    }

    public LeaveType getById(Integer id) {
        return leaveTypeRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "LeaveType not found!!!")
                );
    }
}
