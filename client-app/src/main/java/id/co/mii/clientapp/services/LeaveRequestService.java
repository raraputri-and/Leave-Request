package id.co.mii.clientapp.services;

import id.co.mii.clientapp.models.LeaveRequest;
import id.co.mii.clientapp.models.dto.LeaveRequestRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class LeaveRequestService {
    private RestTemplate restTemplate;

    @Value("${dns.baseUrl}/leave-request")
    private String url;
    @Autowired
    public LeaveRequestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<LeaveRequest> getAll(){
        return restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LeaveRequest>>() {
                }).getBody();
    }

    public LeaveRequest getById(Integer id){
        return restTemplate.exchange(url +"/" +id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<LeaveRequest>() {
                }).getBody();
    }

    public LeaveRequest create(LeaveRequest leaveRequest){
        return restTemplate.exchange(url,
                HttpMethod.POST,
                new HttpEntity(leaveRequest),
                new ParameterizedTypeReference<LeaveRequest>() {
                }).getBody();
    }

    public LeaveRequest update(Integer id, LeaveRequest leaveRequest){
        return restTemplate.exchange(url + "/" + id,
                HttpMethod.PUT,
                new HttpEntity(leaveRequest),
                new ParameterizedTypeReference<LeaveRequest>() {
                }).getBody();
    }

}
