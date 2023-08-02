package id.co.mii.clientapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {
    private Integer id;
    private String nip;
    private String name;
    private String gender;
    private Religion religion;
    private Employee manager;
    private User user;
}
