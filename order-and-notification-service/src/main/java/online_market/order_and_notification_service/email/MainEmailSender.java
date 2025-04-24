package online_market.order_and_notification_service.email;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainEmailSender implements EmailSender {

//    private final JavaMailSender emailSender;

    @Override
    public void sendEmail(String toAddress, String subject, String message) {
        Resend resend = new Resend("re_WnXXzFMA_43oJmCoiiD2UTFqW8YPkL24N");

        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("onboarding@resend.dev")
                .to("nikitaisaev139@gmail.com")
                .subject(subject)
                .html("<strong>"+message+"</strong>")
                .build();

        try {
            CreateEmailResponse data = resend.emails().send(params);
            System.out.println(data.getId());
        } catch (ResendException e) {
            e.printStackTrace();
        }
    }
}
