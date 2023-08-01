package id.co.mii.serverapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import id.co.mii.serverapp.models.LeaveRequest;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {

}
