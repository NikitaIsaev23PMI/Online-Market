package online_market.order_and_notification_service.services;

import online_market.order_and_notification_service.entity.Order;

public interface NotificationService {

    void sendNotificationAboutNewOrderForSeller(Order order);

    void sendNotificationAboutNewOrderForBuyer(Order order);
}
