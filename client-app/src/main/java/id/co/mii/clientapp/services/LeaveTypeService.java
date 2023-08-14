package id.co.mii.clientapp.services;

import id.co.mii.clientapp.models.LeaveType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class LeaveTypeService {
    private RestTemplate restTemplate;

    @Value("${dns.baseUrl}/leave-type")
    private String url;

    @Autowired
    public LeaveTypeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<LeaveType> getAll(){
        return restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LeaveType>>() {
                }).getBody();
    }

    public LeaveType getById(Integer id){
        return restTemplate.exchange(url + "/" +id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<LeaveType>() {
                }).getBody();
    }

}
