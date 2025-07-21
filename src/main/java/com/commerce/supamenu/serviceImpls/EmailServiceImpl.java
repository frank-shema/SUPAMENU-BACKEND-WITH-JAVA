package com.commerce.supamenu.serviceImpls;

import com.commerce.supamenu.config.FrontendProperties;
import com.commerce.supamenu.exceptions.EmailException;
import com.commerce.supamenu.services.IMailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements IMailService {
    private final TemplateEngine templateEngine;
    private final Environment env;
    private final FrontendProperties frontendProperties;
    private final JavaMailSender mailSender;

    @Override
    @Async
    public void sendClientActivationEmail(String toEmail, UUID clientId, String activationToken) {
        try {
            Context context = new Context();
            context.setVariable("clientId", clientId);
            context.setVariable("activationToken", activationToken);
            context.setVariable("activationUrl",
                    frontendProperties.getClientActivationUrl() + "?token=" + activationToken + "&clientId=" + clientId);

            String process = templateEngine.process("client-activation-email", context);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setSubject("Complete Your Restaurant Account Setup");
            helper.setText(process, true);
            helper.setTo(toEmail);
            helper.setFrom(Objects.requireNonNull(env.getProperty("spring.mail.username")));
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new EmailException("Failed to send activation email", e);
        }
    }

    @Override
    @Async
    public void sendPasswordResetEmail(String toEmail, String resetToken) {
        try {
            Context context = new Context();
            context.setVariable("resetUrl",
                    frontendProperties.getPasswordResetUrl() + "?token=" + resetToken);

            String process = templateEngine.process("password-reset-email", context);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setSubject("Password Reset Request");
            helper.setText(process, true);
            helper.setTo(toEmail);
            helper.setFrom(Objects.requireNonNull(env.getProperty("spring.mail.username")));
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new EmailException("Failed to send password reset email", e);
        }
    }

    @Override
    @Async
    public void sendWelcomeEmail(String toEmail, String clientName) {
        try {
            Context context = new Context();
            context.setVariable("clientName", clientName);
            context.setVariable("loginUrl", frontendProperties.getLoginUrl());

            String process = templateEngine.process("welcome-email", context);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setSubject("Welcome to Our Restaurant Platform");
            helper.setText(process, true);
            helper.setTo(toEmail);
            helper.setFrom(Objects.requireNonNull(env.getProperty("spring.mail.username")));
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new EmailException("Failed to send welcome email", e);
        }
    }

    @Override
    @Async
    public void sendRestaurantApprovalEmail(String toEmail, String restaurantName) {
        try {
            Context context = new Context();
            context.setVariable("restaurantName", restaurantName);
            context.setVariable("dashboardUrl", frontendProperties.getClientDashboardUrl());

            String process = templateEngine.process("restaurant-approval-email", context);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setSubject("Your Restaurant " + restaurantName + " Has Been Approved");
            helper.setText(process, true);
            helper.setTo(toEmail);
            helper.setFrom(Objects.requireNonNull(env.getProperty("spring.mail.username")));
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new EmailException("Failed to send approval email", e);
        }
    }
}
