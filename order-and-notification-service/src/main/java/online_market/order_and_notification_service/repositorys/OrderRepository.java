package online_market.order_and_notification_service.repositorys;

import online_market.order_and_notification_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> getAllBySellerUsername(String sellerUsername);

    List<Order> getAllByBuyerUsername(String buyerUsername);
}
