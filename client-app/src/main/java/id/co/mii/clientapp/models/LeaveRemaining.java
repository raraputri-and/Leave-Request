package id.co.mii.clientapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRemaining {
    private Integer id;
    private Integer pastRemaining;
    private Integer presentRemaining;
    private Employee employee;
}
