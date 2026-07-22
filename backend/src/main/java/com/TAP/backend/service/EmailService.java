package com.TAP.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendInviteEmail(String toEmail, String inviteLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("You're invited to join Teacher Assistant Portal");
        message.setText(
                "You have been invited to join as a Professor.\n\n" +
                        "Click the link below to accept and set up your account:\n" +
                        inviteLink + "\n\n" +
                        "This invite will expire in 7 days."
        );
        mailSender.send(message);
    }
}