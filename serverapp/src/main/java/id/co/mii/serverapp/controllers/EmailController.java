package id.co.mii.serverapp.controllers;

import id.co.mii.serverapp.models.dto.request.EmailRequest;
import id.co.mii.serverapp.services.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
@RestController
@AllArgsConstructor
@RequestMapping("/confirmation")
public class EmailController {
    private EmailService emailService;

    @PostMapping
    public void sendHtmlMessage(@RequestBody EmailRequest emailRequest) throws MessagingException {
        emailService.sendHtmlMessage(emailRequest);
    }
}
