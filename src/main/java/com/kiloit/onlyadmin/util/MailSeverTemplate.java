package com.kiloit.onlyadmin.util;

import com.kiloit.onlyadmin.base.BaseService;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.database.entity.UserEntity;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MailSeverTemplate extends BaseService {
    @Value("${spring.mail.username}")
    private String adminEmail;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public StructureRS prepareTemplateMail(String email, UserEntity user, String codeRandom, String subject) {
        Map<String, Object> templateModel = new HashMap<>();
        Context context = new Context();
        try {
            templateModel.put("userName", user.getUsername());
            templateModel.put("userEmail", user.getEmail());
            templateModel.put("date", new Date());
            templateModel.put("code", codeRandom);
            context.setVariables(templateModel);

            String htmlContent = templateEngine.process("email-template", context);
            prepareMailSend(email, htmlContent, subject);
            return response(("Email sent!"));
        } catch (MessagingException e) {
            return response("Error sending email!");
        }
    }

    public void prepareMailSend(String toMail, String htmlContent, String subject) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
        mimeMessageHelper.setFrom(adminEmail);
        mimeMessageHelper.setTo(toMail);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(htmlContent, true);
        javaMailSender.send(message);
    }
}
