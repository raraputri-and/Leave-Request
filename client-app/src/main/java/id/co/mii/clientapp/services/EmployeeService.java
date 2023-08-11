package id.co.mii.clientapp.services;

import id.co.mii.clientapp.models.Employee;
import id.co.mii.clientapp.models.Employee;
import id.co.mii.clientapp.models.User;
import id.co.mii.clientapp.models.dto.EmployeeRequest;
import id.co.mii.clientapp.models.dto.EmployeeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class EmployeeService {
    private RestTemplate restTemplate;

    @Value("${dns.baseUrl}/emp")
    private String url;
    @Autowired
    public EmployeeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<EmployeeResponse> getAll(){
        return restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<EmployeeResponse>>() {
                }).getBody();
    }

    public EmployeeResponse getById(Integer id){
        return restTemplate.exchange(url +"/" +id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<EmployeeResponse>() {
                }).getBody();
    }

    public Employee create(EmployeeRequest employeeRequest){
        return restTemplate.exchange(url ,
                HttpMethod.POST,
                new HttpEntity(employeeRequest),
                new ParameterizedTypeReference<Employee>() {
                }).getBody();
    }

    public Employee update(Integer id, EmployeeRequest employeeRequest){
        return restTemplate.exchange(url + "/" + id,
                HttpMethod.PUT,
                new HttpEntity(employeeRequest),
                new ParameterizedTypeReference<Employee>() {
                }).getBody();
    }

}
