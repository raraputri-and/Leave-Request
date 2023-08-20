package id.co.mii.clientapp.services;

import id.co.mii.clientapp.models.LeaveRequest;
import id.co.mii.clientapp.models.dto.LeaveRequestRequest;
import id.co.mii.clientapp.models.dto.LeaveRequestStatusRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

@Service
@ControllerAdvice
public class LeaveRequestService {
    private RestTemplate restTemplate;

    @Value("${dns.baseUrl}/leave-request")
    private String url;

    @Autowired
    public LeaveRequestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<LeaveRequest> getAll() {
        return restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LeaveRequest>>() {
                }).getBody();
    }

    public LeaveRequest getById(Integer id) {
        return restTemplate.exchange(url + "/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<LeaveRequest>() {
                }).getBody();
    }

    public List<LeaveRequest> getByStatusAction() {
        return restTemplate.exchange(url + "/action",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LeaveRequest>>() {
                }).getBody();
    }

    public List<LeaveRequest> managerAction() {
        return restTemplate.exchange(url + "/manager-tracking",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LeaveRequest>>() {
                }).getBody();
    }

    public List<LeaveRequest> getByCurrentUser() {
        return restTemplate.exchange(url + "/user-tracking",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LeaveRequest>>() {
                }).getBody();
    }

    public LeaveRequest create(LeaveRequestRequest leaveRequestRequest, MultipartFile attachment) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("reason", leaveRequestRequest.getReason());
        body.add("dateStart", leaveRequestRequest.getDateStart());
        body.add("dateEnd", leaveRequestRequest.getDateEnd());
        body.add("quantity", leaveRequestRequest.getQuantity());
        body.add("leaveTypeId", leaveRequestRequest.getLeaveTypeId());
        body.add("attachment", new FileSystemResource(convertMultiPartToFile(attachment)));

        return restTemplate.exchange(url,
                HttpMethod.POST,
                new HttpEntity(body, headers),
                new ParameterizedTypeReference<LeaveRequest>() {
                }).getBody();
    }

    public LeaveRequest accept(Integer id, LeaveRequestStatusRequest leaveRequestStatusRequest) {
        return restTemplate.exchange(url + "/accept" + "/" + id,
                HttpMethod.PUT,
                new HttpEntity(leaveRequestStatusRequest),
                new ParameterizedTypeReference<LeaveRequest>() {
                }).getBody();
    }

    public LeaveRequest reject(Integer id, LeaveRequestStatusRequest leaveRequestStatusRequest) {
        return restTemplate.exchange(url + "/reject" + "/" + id,
                HttpMethod.PUT,
                new HttpEntity(leaveRequestStatusRequest),
                new ParameterizedTypeReference<LeaveRequest>() {
                }).getBody();
    }

    public byte[] showAttachment(Integer id) {
        return restTemplate.exchange(url + "/attachment/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<byte[]>() {
                }).getBody();
    }

    private Path convertMultiPartToFile(MultipartFile file) throws IOException {
        Path filePath = Files.createTempFile("temp-", file.getOriginalFilename());
        Files.write(filePath, file.getBytes());
        return filePath;
    }

}
