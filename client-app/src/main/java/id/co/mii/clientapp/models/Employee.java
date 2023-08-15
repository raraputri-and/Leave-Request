package id.co.mii.clientapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private Integer id;
    private String nip;
    private String name;
    private String email;
    private Date joinDate;
    private String gender;
    private Religion religion;
    private Employee manager;
    private User user;
}
