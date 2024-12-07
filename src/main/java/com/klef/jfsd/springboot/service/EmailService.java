package com.klef.jfsd.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.klef.jfsd.springboot.model.Feedback;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendFeedbackEmail(Feedback feedback) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("trishithagattam@gmail.com"); // Replace with your email
        message.setSubject("New Feedback Submitted");
        message.setText("You have received new feedback:\n\n" +
                "Name: " + feedback.getName() + "\n" +
                "Email: " + feedback.getEmail() + "\n" +
                "Message: " + feedback.getMessage() + "\n\n" +
                "Submitted at: " + feedback.getCreatedAt());
        mailSender.send(message);
    }
}
