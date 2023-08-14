package id.co.mii.serverapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import id.co.mii.serverapp.models.Role;

@Repository
public interface RoleRepository extends JpaRepository <Role,Integer>{
    Optional<Role> findByName(String name);
}
