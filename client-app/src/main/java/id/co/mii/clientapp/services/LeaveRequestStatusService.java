package id.co.mii.clientapp.services;

import id.co.mii.clientapp.models.LeaveRequestStatus;
import id.co.mii.clientapp.models.dto.LeaveRequestStatusRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class LeaveRequestStatusService {
    private RestTemplate restTemplate;

    @Value("${dns.baseUrl}/leave-request-status")
    private String url;
    @Autowired
    public LeaveRequestStatusService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<LeaveRequestStatus> getAll(){
        return restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LeaveRequestStatus>>() {
                }).getBody();
    }

    public LeaveRequestStatus getById(Integer id){
        return restTemplate.exchange(url +"/" +id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<LeaveRequestStatus>() {
                }).getBody();
    }

    public List<LeaveRequestStatus> getByCurrentUser(){
        return restTemplate.exchange(url + "/tracking",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LeaveRequestStatus>>() {
                }).getBody();
    }

    public List<LeaveRequestStatus> getByLeaveRequest(Integer id){
        return restTemplate.exchange(url + "/get-leave-request/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LeaveRequestStatus>>() {
                }).getBody();
    }
}
