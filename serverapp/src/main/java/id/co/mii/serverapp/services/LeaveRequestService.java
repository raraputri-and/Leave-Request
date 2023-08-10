package id.co.mii.serverapp.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import id.co.mii.serverapp.models.*;
import id.co.mii.serverapp.models.dto.request.LeaveRequestStatusRequest;
import id.co.mii.serverapp.repositories.LeaveRemainingRepository;
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
    private LeaveRemainingRepository leaveRemainingRepository;
    private JointLeaveService jointLeaveService;

    public List<LeaveRequest> getAll() {
        return leaveRequestRepository.findAll();
    }

    public LeaveRequest getById(Integer id) {
        return leaveRequestRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Leave Request not found!!"));
    }

    public List<LeaveRequest> getByStatus(){
        User user = userService.getCurrentUser();
        return leaveRequestRepository.findAll()
                .stream()
                .filter(lr -> Objects.equals(lr.getEmployee().getManager().getId(), user.getId()) && lr.getStatusAction().getId()==(3))
                .collect(Collectors.toList());
    }

    public List<LeaveRequest> managerTracking(){
        User user = userService.getCurrentUser();
        return leaveRequestRepository.findAll()
                .stream()
                .filter(lr -> Objects.equals(lr.getEmployee().getManager().getId(), user.getId()))
                .collect(Collectors.toList());
    }

    public List<LeaveRequest> getByCurrentUser(){
        User user = userService.getCurrentUser();
        return leaveRequestRepository.findAll()
                .stream()
                .filter(lr -> Objects.equals(lr.getEmployee().getId(), user.getId()))
                .collect(Collectors.toList());
    }
    @SneakyThrows
    public LeaveRequest create(LeaveRequestRequest leaveRequestRequest) {
        User user = userService.getCurrentUser();
        LeaveRequest leaveRequest = modelMapper.map(leaveRequestRequest, LeaveRequest.class);
        leaveRequest.setEmployee(user.getEmployee());

        jointLeaveService.getAll().forEach(jl -> {
            if (leaveRequestRequest.getDateStart().equals(jl.getDate()) || leaveRequestRequest.getDateEnd().equals(jl.getDate())){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "leave date is already exist");
            }
        });

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

        if(leaveRequestRequest.getLeaveTypeId()==1){
            LeaveRemaining leaveRemaining = user.getEmployee().getLeaveRemaining();
            leaveRemaining.setEmployee(employeeService.getById(user.getEmployee().getId()));
            Integer difference = leaveRemaining.getPastRemaining()-leaveRequest.getQuantity();
            if (leaveRequest.getQuantity()>leaveRemaining.getPresentRemaining()){
                throw new ResponseStatusException(HttpStatus.CONFLICT,"Leave Remaining is lower than your requested days");
            } else if (difference<0){
                leaveRemaining.setPastRemaining(0);
                leaveRemaining.setPresentRemaining(leaveRemaining.getPresentRemaining()+difference);
            } else {
                leaveRemaining.setPastRemaining(leaveRemaining.getPastRemaining()-leaveRequest.getQuantity());
            }
            leaveRemainingRepository.save(leaveRemaining);
        }

        if(leaveRequestRequest.getLeaveTypeId()==2){
            if (leaveRequestRepository.findQuota(2, user.getEmployee().getId()) >= leaveTypeService.getById(2).getQuota()){
                throw new ResponseStatusException(HttpStatus.CONFLICT,"can not choose leave type because run out of quota");
            }
        }
        if(leaveRequestRequest.getLeaveTypeId()==3){
            if (leaveRequestRepository.findQuota(3, user.getEmployee().getId()) >= leaveTypeService.getById(2).getQuota()){
                throw new ResponseStatusException(HttpStatus.CONFLICT,"can not choose leave type because run out of quota");
            }
        }
        if(leaveRequestRequest.getLeaveTypeId()==4){
            if (leaveRequestRepository.findQuota(4, user.getEmployee().getId()) >= leaveTypeService.getById(2).getQuota()){
                throw new ResponseStatusException(HttpStatus.CONFLICT,"can not choose leave type because run out of quota");
            }
        }
        if(leaveRequestRequest.getLeaveTypeId()==5){
            if (leaveRequestRepository.findQuota(5, user.getEmployee().getId()) >= leaveTypeService.getById(2).getQuota()){
                throw new ResponseStatusException(HttpStatus.CONFLICT,"can not choose leave type because run out of quota");
            }
        }
        if(leaveRequestRequest.getLeaveTypeId()==6){
            if (leaveRequestRepository.findQuota(6, user.getEmployee().getId()) >= leaveTypeService.getById(2).getQuota()){
                throw new ResponseStatusException(HttpStatus.CONFLICT,"can not choose leave type because run out of quota");
            }
        }

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