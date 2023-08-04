package id.co.mii.clientapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequestStatus {
    private Integer id;
    private String note;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private Employee pic;
    private StatusAction statusAction;
    private LeaveRequest leaveRequest;
}
