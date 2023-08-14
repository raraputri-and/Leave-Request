package id.co.mii.clientapp.controllers.restcontroller;

import id.co.mii.clientapp.models.LeaveRemaining;
import id.co.mii.clientapp.models.dto.LeaveRemainingRequest;
import id.co.mii.clientapp.services.LeaveRemainingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave-remaining")
@AllArgsConstructor
public class RestLeaveRemainingController {
    private LeaveRemainingService leaveRemainingService;

    @GetMapping
    public List<LeaveRemaining> getAll(){
        return leaveRemainingService.getAll();
    }

    @GetMapping("/{id}")
    public LeaveRemaining getById(@PathVariable Integer id){
        return leaveRemainingService.getById(id);
    }

    @PutMapping("/update/{id}")
    public LeaveRemaining update(@PathVariable Integer id, @RequestBody LeaveRemainingRequest leaveRemainingRequest){
        return leaveRemainingService.update(id,leaveRemainingRequest);
    }
}
