package com.project.demo.rest.passwordResetRequest;

import com.project.demo.logic.entity.passwordResetRequests.PasswordResetRequest;
import com.project.demo.logic.entity.passwordResetRequests.PasswordResetRequestRepository;
import com.project.demo.logic.helper.CodeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/passwordResetRequest")
public class PasswordResetRequestController {
    @Autowired
    PasswordResetRequestRepository passwordResetRequestRepository;

    @PostMapping
    public PasswordResetRequest addPasswordResetRequest(){
        PasswordResetRequest passwordResetRequest = new PasswordResetRequest();
        passwordResetRequest.setResetCode(CodeHelper.generateResetCode(16));
        passwordResetRequest.setExpirationDate(LocalDateTime.now());
        PasswordResetRequest newPasswordResetRequest = passwordResetRequestRepository.save(passwordResetRequest);
        newPasswordResetRequest.setResetCode(null);
        return newPasswordResetRequest;
    }



}
