package id.co.mii.serverapp.repositories;

import id.co.mii.serverapp.models.LeaveRemaining;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRemainingRepository extends JpaRepository<LeaveRemaining, Integer> {
}
