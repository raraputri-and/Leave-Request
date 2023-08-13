package id.co.mii.serverapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequestStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leave_request_status_id", nullable = false)
    private Integer id;
    private String note;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "pic_id", nullable = false)
    private Employee pic;

    @ManyToOne
    @JoinColumn(name = "status_action_id")
    private StatusAction statusAction;

    @ManyToOne
    @JoinColumn(name = "leave_request_id")
    private LeaveRequest leaveRequest;


}
