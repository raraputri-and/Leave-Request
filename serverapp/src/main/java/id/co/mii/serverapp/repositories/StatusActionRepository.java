package id.co.mii.serverapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import id.co.mii.serverapp.models.StatusAction;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusActionRepository extends JpaRepository<StatusAction, Integer> {
    Optional<StatusAction> findById(Integer id);
}
