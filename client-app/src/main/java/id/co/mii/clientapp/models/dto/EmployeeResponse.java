package id.co.mii.clientapp.models.dto;

import id.co.mii.clientapp.models.Religion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {
    private Integer id;
    private String nip;
    private String name;
    private String gender;
    private Religion religion;
    private ManagerResponse manager;
    private String username;
    private String password;
}
