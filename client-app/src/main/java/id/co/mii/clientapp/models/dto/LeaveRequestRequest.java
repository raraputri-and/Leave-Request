package id.co.mii.clientapp.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequestRequest {
    private Integer id;
    private String reason;
    private String dateStart;
    private String dateEnd;
    private Integer quantity;
    private Integer employeeId;
    private Integer leaveTypeId;
    private Integer statusActionId;
}
