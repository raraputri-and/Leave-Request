package id.co.mii.clientapp.services;

import id.co.mii.clientapp.models.Employee;
import id.co.mii.clientapp.models.dto.EmployeeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RegistrationService {
    private RestTemplate restTemplate;

    @Value("${dns.baseUrl}/registration")
    private String url;

    @Autowired
    public RegistrationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean registration(EmployeeRequest employeeRequest) {
        try {
            ResponseEntity<Employee> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity(employeeRequest),
                    new ParameterizedTypeReference<Employee>() {
                    });
            if (response.getStatusCode() == HttpStatus.OK) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
