package id.co.mii.serverapp.services;

import id.co.mii.serverapp.models.Parameter;
import id.co.mii.serverapp.repositories.ParameterRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class ParameterService {
    private ParameterRepository parameterRepository;

    public List<Parameter> getAll() {
        return parameterRepository.findAll();
    }

    public Parameter getById(String id) {
        return parameterRepository.findParameterById(id);
    }

    public Parameter create(Parameter parameter) {
        return parameterRepository.save(parameter);
    }

    public Parameter update(String id, Parameter parameter) {
        getById(id);
        parameter.setId(id);
        return parameterRepository.save(parameter);
    }

    public Parameter delete(String id) {
        Parameter parameter = getById(id);
        parameterRepository.delete(parameter);
        return parameter;
    }
}
