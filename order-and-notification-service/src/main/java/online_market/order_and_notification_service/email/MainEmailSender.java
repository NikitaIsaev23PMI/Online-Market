package online_market.order_and_notification_service.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainEmailSender implements EmailSender {

    private final JavaMailSender emailSender;

    @Override
    public void sendEmail(String toAddress, String subject, String message) {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(toAddress);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(message);
            emailSender.send(simpleMailMessage);
    }
}
