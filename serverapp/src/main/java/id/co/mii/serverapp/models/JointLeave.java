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
public class JointLeave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "joint_leave_id", nullable = false)
    private Integer id;
    @Column(name = "joint_leave_name", nullable = false)
    private String name;
    @Column(name = "joint_leave_date", nullable = false)
    private Date date;
    private Boolean isHoliday;

}
