package id.co.mii.serverapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import id.co.mii.serverapp.models.LeaveType;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, Integer> {
    
}
