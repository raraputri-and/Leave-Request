package id.co.mii.clientapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequest {
    private Integer id;
    private String reason;

    private Date dateStart;

    private Date dateEnd;

    private Integer quantity;
    private String attachment;
    private Employee employee;
    private LeaveType leaveType;
    private StatusAction statusAction;

}
