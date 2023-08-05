package id.co.mii.serverapp.repositories;
import id.co.mii.serverapp.models.Employee;

import id.co.mii.serverapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee ,Integer> {
//    @Query("SELECT e FROM Employee e WHERE e.manager.id = ?1")
    List<Employee> findEmployeeByManagerId(Integer id);
}

