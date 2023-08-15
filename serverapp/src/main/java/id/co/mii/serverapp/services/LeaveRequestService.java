package id.co.mii.serverapp.services;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import id.co.mii.serverapp.models.*;
import id.co.mii.serverapp.models.dto.request.EmailRequest;
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
import org.springframework.web.multipart.MultipartFile;
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
    private EmailService emailService;

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

    public void isDateExist(LocalDate date){
        getAll()
                .stream()
                .filter(lr -> lr.getStatusAction().getId()!=2 &&
                        lr.getEmployee().getUser().equals(userService.getCurrentUser()))
                .forEach(lr -> {
            LocalDate startDateLR = lr.getDateStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate endDateLR = lr.getDateEnd().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (date.equals(endDateLR) || date.equals(startDateLR)){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "leave date is already exist");
            }
            if (date.isBefore(endDateLR) && date.isAfter(startDateLR)){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "leave date is already exist");
            }
        });
    }

    @SneakyThrows
    public LeaveRequest create(LeaveRequestRequest leaveRequestRequest) {
        isDateExist(leaveRequestRequest.getDateStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        isDateExist(leaveRequestRequest.getDateEnd().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
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
            if (leaveRequestRequest.getQuantity()>leaveTypeService.getById(2).getQuantity()){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "quantity cuti menikah can not more than 3");
            }
        }
        if(leaveRequestRequest.getLeaveTypeId()==3){
            if (leaveRequestRepository.findQuota(3, user.getEmployee().getId()) >= leaveTypeService.getById(2).getQuota()){
                throw new ResponseStatusException(HttpStatus.CONFLICT,"can not choose leave type because run out of quota");
            }
            if (leaveRequestRequest.getQuantity()>leaveTypeService.getById(3).getQuantity()){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "quantity cuti keluarga inti meninggal can not more than 2");
            }
        }
        if(leaveRequestRequest.getLeaveTypeId()==4){
            if (leaveRequestRepository.findQuota(4, user.getEmployee().getId()) >= leaveTypeService.getById(2).getQuota()){
                throw new ResponseStatusException(HttpStatus.CONFLICT,"can not choose leave type because run out of quota");
            }
            if (leaveRequestRequest.getQuantity()>leaveTypeService.getById(4).getQuantity()){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "quantity cuti melahirkan can not more than 90");
            }
        }
        if(leaveRequestRequest.getLeaveTypeId()==5){
            if (leaveRequestRepository.findQuota(5, user.getEmployee().getId()) >= leaveTypeService.getById(2).getQuota()){
                throw new ResponseStatusException(HttpStatus.CONFLICT,"can not choose leave type because run out of quota");
            }
            if (leaveRequestRequest.getQuantity()>leaveTypeService.getById(5).getQuantity()){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "quantity cuti haji can not more than 40");
            }
        }
        if(leaveRequestRequest.getLeaveTypeId()==6){
            if (leaveRequestRepository.findQuota(6, user.getEmployee().getId()) >= leaveTypeService.getById(2).getQuota()){
                throw new ResponseStatusException(HttpStatus.CONFLICT,"can not choose leave type because run out of quota");
            }
            if (leaveRequestRequest.getQuantity()>leaveTypeService.getById(6).getQuantity()){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "quantity cuti baptis anak can not more than 1");
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

    @SneakyThrows
    public LeaveRequest accept(Integer id, LeaveRequestStatusRequest leaveRequestStatusRequest){
        LeaveRequest leaveRequest = getById(id);
        leaveRequest.setId(id);
        leaveRequest.setStatusAction(statusActionService.getById(1));

        LeaveRequestStatus leaveRequestStatus = modelMapper.map(leaveRequestStatusRequest, LeaveRequestStatus.class);
        LocalDate localDate = LocalDate.now();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        leaveRequestStatus.setDate(date);
        leaveRequestStatus.setPic(userService.getCurrentUser().getEmployee());
        leaveRequestStatus.setLeaveRequest(getById(id));
        leaveRequestStatus.setStatusAction(leaveRequest.getStatusAction());
        leaveRequestStatusRepository.save(leaveRequestStatus);

        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(leaveRequest.getEmployee().getEmail());
        emailRequest.setSubject("Leave Request Result");
        emailRequest.setTemplate("accepted.html");

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateStart = sdf.format(leaveRequest.getDateStart());
        String dateEnd = sdf.format(leaveRequest.getDateEnd());

        Map<String, Object> map = new HashMap<>();
        map.put("empName", leaveRequest.getEmployee().getName());
        map.put("pic", leaveRequestStatus.getPic().getName());
        map.put("startDate", dateStart);
        map.put("endDate", dateEnd);
        emailRequest.setMap(map);
        emailService.sendHtmlMessage(emailRequest);

        return leaveRequestRepository.save(leaveRequest);
    }
    @SneakyThrows
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

        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(leaveRequest.getEmployee().getEmail());
        emailRequest.setSubject("Leave Request Result");
        emailRequest.setTemplate("rejected.html");

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateStart = sdf.format(leaveRequest.getDateStart());
        String dateEnd = sdf.format(leaveRequest.getDateEnd());

        Map<String, Object> map = new HashMap<>();
        map.put("empName", leaveRequest.getEmployee().getName());
        map.put("pic", leaveRequestStatus.getPic().getName());
        map.put("startDate", dateStart);
        map.put("endDate", dateEnd);
        emailRequest.setMap(map);
        emailService.sendHtmlMessage(emailRequest);

        return leaveRequestRepository.save(leaveRequest);
    }


}