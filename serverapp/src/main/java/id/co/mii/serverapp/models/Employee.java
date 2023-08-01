package id.co.mii.serverapp.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="manager_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Employee manager;

    @OneToMany(mappedBy="manager")
    private Set<Employee> subordinates = new HashSet<Employee>();

    @OneToOne(mappedBy = "employee",cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
}
