package id.co.mii.serverapp.services;

import java.util.List;

import id.co.mii.serverapp.models.Employee;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import id.co.mii.serverapp.models.LeaveRequest;
import id.co.mii.serverapp.models.dto.request.LeaveRequestRequest;
import id.co.mii.serverapp.repositories.LeaveRequestRepository;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class LeaveRequestService {
    private LeaveRequestRepository leaveRequestRepository;
    private EmployeeService employeeService;
    private ModelMapper modelMapper;
    private LeaveTypeService leaveTypeService;
    private StatusActionService statusActionService;

    public List<LeaveRequest> getAll() {
        return leaveRequestRepository.findAll();
    }

    public LeaveRequest getById(Integer id) {
        return leaveRequestRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found!!"));
    }

    public List<LeaveRequest> getByStatus(){
        return leaveRequestRepository.findStatusActionById(3);
    }

    public LeaveRequest create(LeaveRequestRequest leaveRequestRequest) {

         LeaveRequest leaveRequest = modelMapper.map(leaveRequestRequest, LeaveRequest.class);

         leaveRequest.setEmployee(employeeService.getById(leaveRequestRequest.getEmployeeId()));
         leaveRequest.setLeaveType(leaveTypeService.getById(leaveRequestRequest.getLeaveTypeId()));
         leaveRequest.setStatusAction(statusActionService.getById(leaveRequestRequest.getStatusActionId()));

        return leaveRequestRepository.save(leaveRequest);
    }

    public LeaveRequest accept(Integer id, LeaveRequestRequest leaveRequestRequest){
        LeaveRequest leaveRequest = getById(id);
        leaveRequest.setId(id);
        leaveRequest.setStatusAction(statusActionService.getById(1));
        return leaveRequestRepository.save(leaveRequest);
    }

    public LeaveRequest reject(Integer id, LeaveRequestRequest leaveRequestRequest){
        LeaveRequest leaveRequest = getById(id);
        leaveRequest.setId(id);
        leaveRequest.setStatusAction(statusActionService.getById(2));
        return leaveRequestRepository.save(leaveRequest);
    }

}