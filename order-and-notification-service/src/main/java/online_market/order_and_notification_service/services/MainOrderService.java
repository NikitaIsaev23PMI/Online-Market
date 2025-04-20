package online_market.order_and_notification_service.services;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import online_market.order_and_notification_service.entity.Order;
import online_market.order_and_notification_service.repositorys.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainOrderService implements OrderService {

    private final OrderRepository orderRepository;

    private final NotificationService notificationService;

    @Override
    public Order createOrder(Integer productId,String productTitle, Integer count,
                             String sellerUsername, String sellerEmail,
                             String buyerUsername, String buyerEmail,
                             String buyerDetail, String address, String postcode,
                             BigDecimal amount, String paymentType) {

        Order order = orderRepository.save(new Order(null, LocalDateTime.now(),productId,
                productTitle, count,
                sellerUsername,sellerEmail, buyerUsername,
                buyerEmail, buyerDetail, address,
                postcode, amount, paymentType));
        notificationService.sendNotificationAboutNewOrderForBuyer(order);
        notificationService.sendNotificationAboutNewOrderForSeller(order);
        return order;
    }

    @Override
    public List<Order> getAllSellerOrders(String sellerUsername) {
        return this.orderRepository.getAllBySellerUsername(sellerUsername);
    }

    @Override
    public List<Order> getAllBuyerOrders(String buyerUsername) {
        return this.orderRepository.getAllByBuyerUsername(buyerUsername);
    }

}
