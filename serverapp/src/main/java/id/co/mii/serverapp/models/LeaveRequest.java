package id.co.mii.serverapp.models;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leave_request_id", nullable = false)
    private Integer id;

    private String reason;
    private Date dateStart;
    private Date dateEnd;
    private Integer quantity;
    private String attachment;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "leave_type_id")
    private LeaveType leaveType;

    @ManyToOne
    @JoinColumn(name = "status_action_id")
    private StatusAction statusAction;

    @OneToMany(mappedBy = "leaveRequest")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<LeaveRequestStatus> leaveRequestStatus;

}
