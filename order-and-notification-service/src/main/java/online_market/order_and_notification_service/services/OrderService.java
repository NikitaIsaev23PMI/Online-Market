package online_market.order_and_notification_service.services;

import online_market.order_and_notification_service.entity.Order;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    Order createOrder(Integer productId, String productTitle, Integer count,
                      String sellerUsername,String sellerEmail,
                      String buyerUsername, String buyerEmail, String buyerDetail,
                      String address, String postcode, BigDecimal amount,
                      String paymentType);

    List<Order> getAllSellerOrders(String sellerUsername);

    List<Order> getAllBuyerOrders(String buyerUsername);

    Order getOrderById(Integer orderId);

    void updateOrder(String status, LocalDateTime timeOfDelivery, Integer orderId);

    void deleteOrderById(Integer orderId);
}
