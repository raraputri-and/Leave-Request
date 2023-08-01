package id.co.mii.serverapp.services;

import id.co.mii.serverapp.models.JointLeave;
import id.co.mii.serverapp.repositories.JointLeaveRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class JointLeaveService {
    private JointLeaveRepository jointLeaveRepository;

    public List<JointLeave> getAll() {
        return jointLeaveRepository.findAll();
    }

    public JointLeave getById(Integer id) {
        return jointLeaveRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "JointLeave not found!!!")
                );
    }

    public JointLeave create(JointLeave jointLeave) {
        return jointLeaveRepository.save(jointLeave);
    }

    public JointLeave update(Integer id, JointLeave jointLeave) {
        getById(id);
        jointLeave.setId(id);
        return jointLeaveRepository.save(jointLeave);
    }

    public JointLeave delete(Integer id) {
        JointLeave jointLeave = getById(id);
        jointLeaveRepository.delete(jointLeave);
        return jointLeave;
    }
}
