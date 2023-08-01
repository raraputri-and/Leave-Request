package id.co.mii.serverapp.repositories;

import id.co.mii.serverapp.models.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Integer> {
    @Query("SELECT p FROM Parameter p WHERE p.id = ?1")
    Parameter findParameterById(String id);
}
