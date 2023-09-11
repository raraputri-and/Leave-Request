package id.co.mii.serverapp.models.dto.request;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequestRequest {
    private String reason;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateEnd;
    private Integer quantity;
    private MultipartFile attachment;
    private Integer employeeId;
    private Integer leaveTypeId;
    private Integer statusActionId;
}
