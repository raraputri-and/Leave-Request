package id.co.mii.serverapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Parameter {
    @Id
    @Column(name = "parameter_id", nullable = false)
    private String id;
    @Column(name = "parameter_leave_qty", nullable = false)
    private String leaveQty;
    @Column(name = "parameter_note")
    private String note;
}
