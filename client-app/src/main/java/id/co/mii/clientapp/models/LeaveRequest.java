package id.co.mii.clientapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequest {
    private Integer id;
    private String reason;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateEnd;
    private byte[] attachment;

    private Integer quantity;
    private Employee employee;
    private LeaveType leaveType;
    private StatusAction statusAction;

}
