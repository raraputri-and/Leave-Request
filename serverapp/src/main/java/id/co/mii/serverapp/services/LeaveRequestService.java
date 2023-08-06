package id.co.mii.serverapp.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import id.co.mii.serverapp.models.*;
import id.co.mii.serverapp.models.dto.request.LeaveRequestStatusRequest;
import id.co.mii.serverapp.repositories.EmployeeRepository;
import id.co.mii.serverapp.repositories.LeaveRequestStatusRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
    private UserService userService;

    public List<LeaveRequest> getAll() {
        return leaveRequestRepository.findAll();
    }

    public LeaveRequest getById(Integer id) {
        return leaveRequestRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found!!"));
    }

    public List<LeaveRequest> getByStatus(){
        User user = userService.getCurrentUser();
        return leaveRequestRepository.findAll()
                .stream()
                .filter(lr -> Objects.equals(lr.getEmployee().getManager().getId(), user.getId()) && lr.getStatusAction().getId()==(3))
                .collect(Collectors.toList());
    }
    @SneakyThrows
    public LeaveRequest create(LeaveRequestRequest leaveRequestRequest) {
        User user = userService.getCurrentUser();
        LeaveRequest leaveRequest = modelMapper.map(leaveRequestRequest, LeaveRequest.class);
        leaveRequest.setEmployee(user.getEmployee());

        if(leaveRequestRequest.getLeaveTypeId()==4 && !user.getEmployee().getGender().equals(Gender.Female)){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Employee is Male, can not choose Birth Leave");
        }
        if(leaveRequestRequest.getLeaveTypeId()==5 && user.getEmployee().getReligion().getId()!=1){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Employee is not Moslem, can not choose cuti haji");
        }
        if(leaveRequestRequest.getLeaveTypeId()==6 && user.getEmployee().getReligion().getId()!=2){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Employee is not christian, can not choose cuti baptis");
        }
        if(leaveRequestRequest.getLeaveTypeId()==6 && user.getEmployee().getReligion().getId()!=3){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Employee is not catholic , can not choose cuti baptis");
        }
        leaveRequest.setLeaveType(leaveTypeService.getById(leaveRequestRequest.getLeaveTypeId()));
        leaveRequest.setStatusAction(statusActionService.getById(3));

        leaveRequest = leaveRequestRepository.save(leaveRequest);

        LeaveRequestStatusRequest leaveRequestStatusRequest = new LeaveRequestStatusRequest();
        LeaveRequestStatus leaveRequestStatus = modelMapper.map(leaveRequestStatusRequest, LeaveRequestStatus.class);
        LocalDate localDate = LocalDate.now();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        leaveRequestStatus.setDate(date);
        leaveRequestStatus.setPic(userService.getCurrentUser().getEmployee());
        leaveRequestStatus.setLeaveRequest(leaveRequest);
        leaveRequestStatus.setStatusAction(leaveRequest.getStatusAction());
        leaveRequestStatusRepository.save(leaveRequestStatus);


        return leaveRequest;
    }


    public LeaveRequest accept(Integer id){
        LeaveRequest leaveRequest = getById(id);
        leaveRequest.setId(id);
        leaveRequest.setStatusAction(statusActionService.getById(1));

        LeaveRequestStatusRequest leaveRequestStatusRequest = new LeaveRequestStatusRequest();
        LeaveRequestStatus leaveRequestStatus = modelMapper.map(leaveRequestStatusRequest, LeaveRequestStatus.class);
        LocalDate localDate = LocalDate.now();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        leaveRequestStatus.setDate(date);
        leaveRequestStatus.setPic(userService.getCurrentUser().getEmployee());
        leaveRequestStatus.setLeaveRequest(getById(id));
        leaveRequestStatus.setStatusAction(leaveRequest.getStatusAction());
        leaveRequestStatusRepository.save(leaveRequestStatus);
        return leaveRequestRepository.save(leaveRequest);
    }

    public LeaveRequest reject(Integer id, LeaveRequestStatusRequest leaveRequestStatusRequest){
        LeaveRequest leaveRequest = getById(id);
        leaveRequest.setId(id);
        leaveRequest.setStatusAction(statusActionService.getById(2));

        LeaveRequestStatus leaveRequestStatus = modelMapper.map(leaveRequestStatusRequest, LeaveRequestStatus.class);
        LocalDate localDate = LocalDate.now();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        leaveRequestStatus.setDate(date);
        leaveRequestStatus.setPic(userService.getCurrentUser().getEmployee());
        leaveRequestStatus.setLeaveRequest(getById(id));
        leaveRequestStatus.setStatusAction(leaveRequest.getStatusAction());
        leaveRequestStatusRepository.save(leaveRequestStatus);
        return leaveRequestRepository.save(leaveRequest);
    }

}