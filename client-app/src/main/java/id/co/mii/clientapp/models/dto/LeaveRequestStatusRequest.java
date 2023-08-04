package id.co.mii.clientapp.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequestStatusRequest {
    private Integer id;
    private String note;
    private Integer pic;
    private Integer statusActionId;
    private Integer leaveRequestId;
}
