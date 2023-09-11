package id.co.mii.serverapp.services;

import id.co.mii.serverapp.models.JointLeave;
import id.co.mii.serverapp.models.LeaveRemaining;
import id.co.mii.serverapp.models.LeaveRequest;
import id.co.mii.serverapp.models.dto.request.LeaveRequestRequest;
import id.co.mii.serverapp.repositories.JointLeaveRepository;
import id.co.mii.serverapp.repositories.LeaveRemainingRepository;
import id.co.mii.serverapp.repositories.LeaveRequestRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JointLeaveService {
    private JointLeaveRepository jointLeaveRepository;
    private LeaveRemainingService leaveRemainingService;
    private LeaveRemainingRepository leaveRemainingRepository;

    public List<JointLeave> getAll() {
        return jointLeaveRepository.findAll();
    }

    public JointLeave getById(Integer id) {
        return jointLeaveRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "JointLeave not found!!!")
                );
    }

    public JointLeave create(JointLeave jointLeave) {
        List<LeaveRemaining> leaveRemainings = leaveRemainingService.getAll();
        if (!jointLeave.getIsHoliday()){
            List<LeaveRemaining> lr = new ArrayList<>();
            leaveRemainings.forEach(lrs -> {
                lrs.setPresentRemaining(lrs.getPresentRemaining()-1);
                lr.add(lrs);
            });
            leaveRemainingRepository.saveAll(lr);

//            employeeService.getAll().forEach(emp -> {
//                LeaveRequest leaveRequest = new LeaveRequest();
//                leaveRequest.setReason(jointLeave.getName());
//                leaveRequest.setDateStart(jointLeave.getDate());
//                leaveRequest.setDateEnd(jointLeave.getDate());
//                leaveRequest.setQuantity(1);
//                leaveRequest.setLeaveType(leaveTypeService.getById(1));
//                leaveRequest.setEmployee(emp);
//                leaveRequest.setStatusAction(statusActionService.getById(1));
//                leaveRequestRepository.save(leaveRequest);
//            });

        }
        return jointLeaveRepository.save(jointLeave);
    }
    public JointLeave update(Integer id, JointLeave jointLeave) {
        JointLeave oldJointLeave = getById(id);
        jointLeave.setId(id);
        if (!oldJointLeave.getIsHoliday().equals(jointLeave.getIsHoliday())){
            List<LeaveRemaining> leaveRemainings = leaveRemainingService.getAll().stream().map(lr -> {
                if (jointLeave.getIsHoliday()){
                    lr.setPresentRemaining(lr.getPresentRemaining()+1);
                } else {
                    lr.setPresentRemaining(lr.getPresentRemaining()-1);
                }
                return lr;
            }).collect(Collectors.toList());
            leaveRemainingRepository.saveAll(leaveRemainings);
        }
        return jointLeaveRepository.save(jointLeave);
    }

    public JointLeave delete(Integer id) {
        JointLeave jointLeave = getById(id);
        if (!jointLeave.getIsHoliday()){
            List<LeaveRemaining> leaveRemainings = leaveRemainingService.getAll().stream().map(lr -> {
                lr.setPresentRemaining(lr.getPresentRemaining()+1);
                return lr;
            }).collect(Collectors.toList());
            leaveRemainingRepository.saveAll(leaveRemainings);
        }
        jointLeaveRepository.delete(jointLeave);
        return jointLeave;
    }
}
