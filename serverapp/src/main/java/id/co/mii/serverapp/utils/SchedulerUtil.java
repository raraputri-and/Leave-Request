package id.co.mii.serverapp.utils;

import id.co.mii.serverapp.models.JointLeave;
import id.co.mii.serverapp.models.LeaveRemaining;
import id.co.mii.serverapp.repositories.JointLeaveRepository;
import id.co.mii.serverapp.repositories.LeaveRemainingRepository;
import id.co.mii.serverapp.services.LeaveRemainingService;
import id.co.mii.serverapp.services.ParameterService;
import id.co.mii.serverapp.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class SchedulerUtil {
    private ParameterService parameterService;
    private LeaveRemainingService leaveRemainingService;
    private LeaveRemainingRepository leaveRemainingRepository;
    private RestTemplate restTemplate;
    private JointLeaveRepository jointLeaveRepository;
    private UserService userService;
    @Scheduled(cron = "00 00 00 01 01 ?")
    public void resetPresentRemaining() {
        List<LeaveRemaining> leaveRemaining = leaveRemainingService.getAll();
        List<LeaveRemaining> lr = new ArrayList<>();
        leaveRemaining.forEach(lrs -> {
            lrs.setPastRemaining(lrs.getPresentRemaining());
            lrs.setPresentRemaining(Integer.valueOf(parameterService.getById("Max-leave").getLeaveQty()));
            lr.add(lrs);
        });
        leaveRemainingRepository.saveAll(lr);
    }

    @Scheduled(cron = "0 00 00 01 08 ?")
    public void resetPastRemaining() {
        System.out.println("scheduler is running...");
        List<LeaveRemaining> leaveRemaining = leaveRemainingService.getAll();
        List<LeaveRemaining> lr = new ArrayList<>();
        leaveRemaining.forEach(lrs -> {
            lrs.setPastRemaining(0);
            lr.add(lrs);
        });
        leaveRemainingRepository.saveAll(lr);
    }

    @Scheduled(cron = "15 00 00 01 01 ?")
    public void holidayFromAPI(){
        System.out.println("add holiday from api...");
        List<Map<String, Object>> holidays = restTemplate.exchange("https://api-harilibur.vercel.app/api",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Map<String, Object>>>() {
                }).getBody();
        AtomicReference<Integer> count = new AtomicReference<>(0);
        List<JointLeave> jointLeaves = holidays.stream().map(holiday -> {
            JointLeave jointLeave = new JointLeave();
            jointLeave.setName((String) holiday.get("holiday_name"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            String dateInString = (String) holiday.get("holiday_date");
            try {
                Date date = formatter.parse(dateInString);
                jointLeave.setDate(date);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            jointLeave.setIsHoliday((Boolean) holiday.get("is_national_holiday"));
            if (!jointLeave.getIsHoliday()){
                count.getAndSet(count.get() + 1);
                System.out.println(count.get());
            }
            return jointLeave;
        }).collect(Collectors.toList());
        jointLeaveRepository.saveAll(jointLeaves);
        userService.getAll().forEach(user -> {
            LeaveRemaining leaveRemaining = user.getEmployee().getLeaveRemaining();
            Integer difference = leaveRemaining.getPresentRemaining()-count.get();
            leaveRemaining.setPresentRemaining(difference);
            leaveRemainingRepository.save(leaveRemaining);
        });
    }
}


