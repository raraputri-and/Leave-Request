package id.co.mii.serverapp.repositories;

import id.co.mii.serverapp.models.LeaveRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRequestStatusRepository extends JpaRepository<LeaveRequestStatus, Integer> {
}
