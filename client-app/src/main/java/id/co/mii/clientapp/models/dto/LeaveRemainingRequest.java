package id.co.mii.clientapp.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRemainingRequest {
    private Integer id;
    private Integer pastRemaining;
    private Integer presentRemaining;
    private Integer employeeId;
}
