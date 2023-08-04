package id.co.mii.serverapp.models.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequestStatusRequest {
    private String note;
    private Date date;
    private Integer pic;
    private Integer statusActionId;
    private Integer leaveRequestId;

}
