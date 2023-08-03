package id.co.mii.clientapp.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequestRequest {
    private Integer id;
    private String reason;
    private Date dateStart;
    private Date dateEnd;
    private Integer quantity;
    private String attachment;
    private Integer employeeId;
    private Integer leaveTypeId;
    private Integer statusActionId;
}
