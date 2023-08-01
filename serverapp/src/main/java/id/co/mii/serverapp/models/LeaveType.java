package id.co.mii.serverapp.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class LeaveType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leave_type_id", nullable = false)
    private Integer id;

    @Column(name = "leave_type_name",  nullable = false)
    private String name;

    @OneToMany(mappedBy = "leaveType")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<LeaveRequest> leaveRequests;
}
