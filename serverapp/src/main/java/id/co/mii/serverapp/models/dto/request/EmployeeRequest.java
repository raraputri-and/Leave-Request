package id.co.mii.serverapp.models.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {
    private String nip;
    private String name;
    private String gender;
    private Integer religionId;
    private Integer managerId;
    private String username;
    private String password;
}
