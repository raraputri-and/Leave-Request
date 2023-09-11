package id.co.mii.serverapp.models.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {
    private String nip;
    private String name;
    private String email;
    private Date joinDate;
    private String gender;
    private Integer religionId;
    private Integer managerId;
    private String username;
    private String password;
}
