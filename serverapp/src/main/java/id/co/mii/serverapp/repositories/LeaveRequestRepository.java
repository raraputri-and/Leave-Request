package id.co.mii.serverapp.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import id.co.mii.serverapp.models.LeaveRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {

    @Query("SELECT lr FROM LeaveRequest lr WHERE lr.statusAction.id = ?1")
    List<LeaveRequest> findStatusActionById(Integer id);


}
