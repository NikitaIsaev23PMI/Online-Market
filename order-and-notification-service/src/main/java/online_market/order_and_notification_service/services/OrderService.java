package online_market.order_and_notification_service.services;

import online_market.order_and_notification_service.entity.Order;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    Order createOrder(Integer productId, String productTitle, Integer count,
                      String sellerSubject,String sellerEmail,
                      String buyerSubject, String buyerEmail, String buyerDetail,
                      String address, String postcode, BigDecimal amount,
                      String paymentType);

    List<Order> getAllSellerOrders(String sellerSubject);

    List<Order> getAllBuyerOrders(String buyerSubject);
}
