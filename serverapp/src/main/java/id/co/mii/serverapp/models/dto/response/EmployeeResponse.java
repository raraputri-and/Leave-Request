package id.co.mii.serverapp.models.dto.response;

import id.co.mii.serverapp.models.Religion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {
    private Integer id;
    private String nip;
    private String name;
    private String email;
    private Date joinDate;
    private String gender;
    private Religion religion;
    private ManagerResponse manager;
    private String username;
    private String password;
}
