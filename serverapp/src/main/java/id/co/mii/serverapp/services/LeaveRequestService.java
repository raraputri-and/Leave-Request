package id.co.mii.serverapp.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import id.co.mii.serverapp.models.Employee;
import id.co.mii.serverapp.models.LeaveRequestStatus;
import id.co.mii.serverapp.models.dto.request.LeaveRequestStatusRequest;
import id.co.mii.serverapp.repositories.LeaveRequestStatusRepository;
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
    private LeaveRequestStatusRepository leaveRequestStatusRepository;

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

        LeaveRequestStatusRequest leaveRequestStatusRequest = new LeaveRequestStatusRequest();
        LeaveRequestStatus leaveRequestStatus = modelMapper.map(leaveRequestStatusRequest, LeaveRequestStatus.class);
        LocalDate localDate = LocalDate.now();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        leaveRequestStatus.setDate(date);
        leaveRequestStatus.setPic(employeeService.getById(2));
        leaveRequestStatus.setLeaveRequest(getById(id));
        leaveRequestStatus.setStatusAction(leaveRequest.getStatusAction());
        leaveRequestStatusRepository.save(leaveRequestStatus);
        return leaveRequestRepository.save(leaveRequest);
    }

    public LeaveRequest reject(Integer id, LeaveRequestRequest leaveRequestRequest){
        LeaveRequest leaveRequest = getById(id);
        leaveRequest.setId(id);
        leaveRequest.setStatusAction(statusActionService.getById(2));

        LeaveRequestStatusRequest leaveRequestStatusRequest = new LeaveRequestStatusRequest();
        LeaveRequestStatus leaveRequestStatus = modelMapper.map(leaveRequestStatusRequest, LeaveRequestStatus.class);
        LocalDate localDate = LocalDate.now();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        leaveRequestStatus.setDate(date);
        leaveRequestStatus.setPic(employeeService.getById(2));
        leaveRequestStatus.setLeaveRequest(getById(id));
        leaveRequestStatus.setStatusAction(leaveRequest.getStatusAction());
        leaveRequestStatusRepository.save(leaveRequestStatus);
        return leaveRequestRepository.save(leaveRequest);
    }

}