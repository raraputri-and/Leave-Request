package id.co.mii.serverapp.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false)
    private Integer id;

    @Column(name = "employee_nip", nullable = false)
    private String nip;

    @Column(name = "employee_name", nullable = false)
    private String name;

    @Column(name = "employee_gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "religion_id")
    private Religion religion;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="manager_id")
    @JsonBackReference
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Employee manager;


    @OneToMany(mappedBy = "manager")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Employee> subordinates = new HashSet<Employee>();

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @OneToMany(mappedBy = "employee")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<LeaveRequest> leaveRequests;

    @OneToMany(mappedBy = "pic")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<LeaveRequestStatus> leaveRequestStatus;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    @JoinColumn(name = "leave_remaining_id", referencedColumnName = "leave_remaining_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LeaveRemaining leaveRemaining;
}
