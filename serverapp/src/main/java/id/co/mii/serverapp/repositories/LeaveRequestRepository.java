package id.co.mii.serverapp.repositories;


import id.co.mii.serverapp.models.LeaveType;
import id.co.mii.serverapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import id.co.mii.serverapp.models.LeaveRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {

    @Query("SELECT count(lr) FROM LeaveRequest lr WHERE lr.leaveType.id = ?1 and lr.employee.id = ?2")
    Integer findQuota(Integer LeaveTypeId, Integer userId);

}
