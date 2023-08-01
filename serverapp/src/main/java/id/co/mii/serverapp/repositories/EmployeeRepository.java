package id.co.mii.serverapp.repositories;
import id.co.mii.serverapp.models.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee ,Integer> {

}

