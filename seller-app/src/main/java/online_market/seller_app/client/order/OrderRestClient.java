package online_market.seller_app.client.order;

import online_market.seller_app.entity.Order;

import java.util.List;

public interface OrderRestClient {

    Order getSellerOrder(Integer orderId);

    List<Order> getSellerOrders(String username);
}
