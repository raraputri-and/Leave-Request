package id.co.mii.serverapp.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRemaining {

    @Id
    @Column(name = "leave_remaining_id")
    private Integer id;

    @Column(name = "past_remaining")
    private Integer pastRemaining;

    @Column(name = "present_remaining")
    private Integer presentRemaining;

    @OneToOne
    @MapsId
    @JoinColumn(name = "leave_remaining_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Employee employee;
}
