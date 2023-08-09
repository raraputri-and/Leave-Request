package id.co.mii.clientapp.services;

import id.co.mii.clientapp.models.LeaveRequest;
import id.co.mii.clientapp.models.dto.LeaveRequestRequest;
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

    public List<LeaveRequest> getByStatusAction(){
        return restTemplate.exchange(url + "/action",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LeaveRequest>>() {
                }).getBody();
    }

    public List<LeaveRequest> managerAction(){
        return restTemplate.exchange(url + "/manager-tracking",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LeaveRequest>>() {
                }).getBody();
    }

    public List<LeaveRequest> getByCurrentUser(){
        return restTemplate.exchange(url + "/user-tracking",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LeaveRequest>>() {
                }).getBody();
    }

    public LeaveRequest create(LeaveRequestRequest leaveRequestRequest){
        return restTemplate.exchange(url,
                HttpMethod.POST,
                new HttpEntity(leaveRequestRequest),
                new ParameterizedTypeReference<LeaveRequest>() {
                }).getBody();
    }

    public LeaveRequest accept(Integer id){
        return restTemplate.exchange(url +"/accept"+ "/" + id,
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<LeaveRequest>() {
                }).getBody();
    }

    public LeaveRequest reject(Integer id, LeaveRequestStatusRequest leaveRequestStatusRequest){
        return restTemplate.exchange(url +"/reject"+ "/" + id,
                HttpMethod.PUT,
                new HttpEntity(leaveRequestStatusRequest),
                new ParameterizedTypeReference<LeaveRequest>() {
                }).getBody();
    }

}
