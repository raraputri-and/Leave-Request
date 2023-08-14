package id.co.mii.serverapp.repositories;
import id.co.mii.serverapp.models.Parameter;
import id.co.mii.serverapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByUsername( String username);

}

