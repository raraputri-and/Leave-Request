package id.co.mii.serverapp.utils;

import id.co.mii.serverapp.models.LeaveRemaining;
import id.co.mii.serverapp.repositories.LeaveRemainingRepository;
import id.co.mii.serverapp.services.LeaveRemainingService;
import id.co.mii.serverapp.services.ParameterService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class SchedulerUtil {
    private ParameterService parameterService;
    private LeaveRemainingService leaveRemainingService;
    private LeaveRemainingRepository leaveRemainingRepository;
    @Scheduled(cron = "00 00 00 31 12 ?")
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
}


