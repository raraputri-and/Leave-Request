package id.co.mii.serverapp.repositories;

import id.co.mii.serverapp.models.JointLeave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JointLeaveRepository extends JpaRepository<JointLeave, Integer> {
}
