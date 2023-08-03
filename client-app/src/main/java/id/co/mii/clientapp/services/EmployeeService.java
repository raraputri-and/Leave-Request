package id.co.mii.clientapp.services;

import id.co.mii.clientapp.models.Employee;
import id.co.mii.clientapp.models.dto.EmployeeRequest;
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
    @Autowired //untuk mengambil objek nya dari spring container
    public EmployeeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Employee> getAll(){
        return restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Employee>>() {
                }).getBody();
    }

    public Employee getById(Integer id){
        return restTemplate.exchange(url +"/" +id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Employee>() {
                }).getBody();
    }

    public Employee create(EmployeeRequest employeeRequest){
        return restTemplate.exchange(url,
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

    public Employee delete(Integer id){
        return restTemplate.exchange(url + "/" + id,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<Employee>() {
                }).getBody();
    }
}
