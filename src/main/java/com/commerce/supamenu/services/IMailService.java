package com.commerce.supamenu.services;

import java.util.UUID;

public interface IMailService {
    void sendClientActivationEmail(String toEmail, UUID clientId, String activationToken);
    void sendPasswordResetEmail(String toEmail, String resetToken);
    void sendWelcomeEmail(String toEmail, String clientName);
    void sendRestaurantApprovalEmail(String toEmail, String restaurantName);
}
