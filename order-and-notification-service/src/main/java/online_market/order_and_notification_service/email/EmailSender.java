package online_market.order_and_notification_service.email;

public interface EmailSender {

    void sendEmail(String toAddress, String subject, String message);
}
