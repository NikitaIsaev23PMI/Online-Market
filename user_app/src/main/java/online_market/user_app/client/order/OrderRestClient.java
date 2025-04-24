package online_market.user_app.client.order;

import online_market.user_app.entity.Order;

import java.math.BigDecimal;
import java.util.List;

public interface OrderRestClient {

    Order newOrder(String sellerUsername,
                   String sellerEmail,
                   String productTitle,
                   Integer count,
                   String buyerUsername,
                   Integer productId,
                   String buyerEmail,
                   String buyerDetail,
                   String address,
                   String postcode,
                   BigDecimal amount,
                   String paymentType);

    List<Order> getAllUserOrders(String username);

    Order getUserOrder(Integer orderId);
}
