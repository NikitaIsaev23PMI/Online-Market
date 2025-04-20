package online_market.order_and_notification_service.services;

import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import online_market.order_and_notification_service.entity.Order;
import online_market.order_and_notification_service.email.EmailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MainNotificationService implements NotificationService {

    private final EmailSender emailSender;

    @Override
    public void sendNotificationAboutNewOrderForSeller(Order order) {

        String subject = "Новый Заказ! в Онлайн-Магазине";

        String message = "Здравствуйте уважаемый " + order.getSellerUsername() + " Пользователь Онлайн-Магазина " +
                order.getBuyerUsername() + " Заказал у вас " + order.getProductTitle() + " в Колличестве "
                + order.getCount() + ".\n"
                +"Время заказа: " + order.getTimeOfCreated() + "\n"
                +"Общая стоимость заказа: " + order.getAmount() + "\n"
                +"Доставить по адресу: " + order.getAddress() + "\n"
                +"Почтовый индекс: " + order.getPostcode() + "\n"
                +"Выбранный тит оплаты: " + order.getPaymentType();

        this.emailSender.sendEmail(order.getSellerEmail(),subject, message);
    }

    @Override
    public void sendNotificationAboutNewOrderForBuyer(Order order) {
        String subject = "Детали заказа в Онлайн-Магазине";

        String message = "Здравствуйте уважаемый " + order.getBuyerDetail() + " Вы заказали " +
        order.getProductTitle() + " в колличестве " + order.getCount() + " штук " + "на общую сумму "
        +order.getAmount() + " у " +order.getSellerUsername() + "\n"
                +"Время заказа: " + order.getTimeOfCreated() + "\n"
                +"Выбранный тит оплаты: " + order.getPaymentType();

        this.emailSender.sendEmail(order.getSellerEmail(),subject, message);
    }

}
