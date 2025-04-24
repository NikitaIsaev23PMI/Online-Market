package online_market.order_and_notification_service.services;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import online_market.order_and_notification_service.entity.Order;
import online_market.order_and_notification_service.enums.OrderStatus;
import online_market.order_and_notification_service.repositorys.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

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

        Order order = orderRepository.save(new Order(null, null, null,null, OrderStatus.ASSEMBLY,
                productId,
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

    @Override
    public Order getOrderById(Integer orderId) {
        if (this.orderRepository.existsById(orderId)) {
            return this.orderRepository.findById(orderId).get();
        } else {
            throw new NoSuchElementException("Заказ не найден");
        }
    }

    @Override
    public void updateOrder(String status, LocalDateTime timeOfDelivery, Integer orderId) {
        this.orderRepository.findById(orderId).ifPresentOrElse(
                order -> {
                    order.setStatus(OrderStatus.fromString(status));
                    order.setTimeOfDelivered(timeOfDelivery);
                    this.orderRepository.save(order);
                },() -> {throw new NoSuchElementException("Заказ не найден");}
        );

    }

}
