package id.co.mii.serverapp.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.co.mii.serverapp.models.Employee;
import id.co.mii.serverapp.models.LeaveRemaining;
import id.co.mii.serverapp.models.LeaveRequest;
import id.co.mii.serverapp.models.LeaveType;
import id.co.mii.serverapp.models.StatusAction;
import id.co.mii.serverapp.models.dto.request.LeaveRequestRequest;
import id.co.mii.serverapp.repositories.LeaveRequestRepository;
import id.co.mii.serverapp.repositories.LeaveTypeRepository;
import id.co.mii.serverapp.repositories.StatusActionRepository;

@Service
public class LeaveRequestService {
    private LeaveRequestRepository leaveRequestRepository;
    private EmployeeService employeeService;
    private LeaveTypeRepository leaveTypeRepository;
    private StatusActionRepository statusActionRepository;
    private ModelMapper modelMapper;

    @Autowired
    public LeaveRequestService(LeaveRequestRepository leaveRequestRepository, EmployeeService employeeService,
            LeaveTypeRepository leaveTypeRepository,
            StatusActionRepository statusActionRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.employeeService = employeeService;
        this.leaveTypeRepository = leaveTypeRepository;
        this.statusActionRepository = statusActionRepository;
    }

    // Implement methods to manage leave requests (e.g., create, read, update,
    // delete, etc.)

    public LeaveRequest createLeaveRequest(LeaveRequestRequest leaveRequestRequest) {

        // LeaveRequest leaveRequest = modelMapper.map(leaveRequestRequest,
        // LeaveRequest.class);
        // LeaveType leaveType = modelMapper.map(leaveRequestRequest, LeaveType.class);
        // StatusAction statusAction = modelMapper.map(leaveRequestRequest,
        // StatusAction.class);

        // Employee employee =
        // employeeService.getById(leaveRequestRequest.getEmployeeId());
        // LeaveType leaveType =
        // leaveTypeRepository.findById(leaveRequestRequest.getLeaveTypeId()).orElse(null);
        // LeaveStatusAction statusAction =
        // leaveStatusActionRepository.findById(leaveRequestRequest.getStatusActionId())
        // .orElse(null);

        // if (employee == null || leaveType == null || statusAction == null) {
        // throw new IllegalArgumentException("Invalid employee, leave type, or status
        // action.");
        // }

        // leaveRequest.setEmployee(employee);
        // leaveRequest.setLeaveType(leaveType);
        // leaveRequest.setStatusAction(statusAction);

        Employee employee = employeeService.getById(leaveRequestRequest.getEmployeeId().intValue());
        LeaveType leaveType = leaveTypeRepository.findById(leaveRequestRequest.getLeaveTypeId().intValue())
                .orElse(null);
        StatusAction statusAction = statusActionRepository
                .findById(leaveRequestRequest.getStatusActionId().intValue()).orElse(null);

        if (employee == null || leaveType == null || statusAction == null) {
            throw new IllegalArgumentException("Invalid employee, leave type, or status action.");
        }

        LeaveRequest leaveRequest = modelMapper.map(leaveRequestRequest, LeaveRequest.class);
        leaveRequest.setEmployee(employee);
        leaveRequest.setLeaveType(leaveType);
        leaveRequest.setStatusAction(statusAction);
        // Perform any other necessary validations or business logic here

        // Update leaveRemaining for the employee (adjust remaining leave counts)
        LeaveRemaining leaveRemaining = employee.getLeaveRemaining();
        if (leaveRemaining == null) {
            leaveRemaining = new LeaveRemaining();
            leaveRemaining.setEmployee(employee);
            leaveRemaining.setPastRemaining(12); // You can set initial values as needed
            leaveRemaining.setPresentRemaining(12);
            employee.setLeaveRemaining(leaveRemaining);
        }
        int daysRequested = calculateDaysBetweenDates(leaveRequest.getDateStart(), leaveRequest.getDateEnd());
        int remainingAfterRequest = leaveRemaining.getPastRemaining() - daysRequested;
        leaveRemaining.setPastRemaining(remainingAfterRequest);

        // Save the updated entities
        leaveRequestRepository.save(leaveRequest);
        employeeService.saveEmployee(employee);

        return leaveRequest;
    }

    // Implement other leave request management methods as needed

    private int calculateDaysBetweenDates(Date startDate, Date endDate) {
        // Convert the given Date objects to LocalDate
        LocalDate localStartDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localEndDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Calculate the number of days between the two LocalDate objects
        long daysBetween = ChronoUnit.DAYS.between(localStartDate, localEndDate);

        // Convert the result to an int and return
        return Math.toIntExact(daysBetween);
    }

}