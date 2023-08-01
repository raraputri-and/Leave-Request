package id.co.mii.serverapp.models.dto.request;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequestRequest {
    private String reason;
    private Date dateStart;
    private Date dateEnd;
    private Integer quantity;
    private String attachment;
    private Integer employeeId;
    private Integer leaveTypeId;
    private Integer statusActionId;

}
