package online_market.products_service.services.discount;

import online_market.products_service.entity.Discount;

import java.time.LocalDateTime;

public interface DiscountService {

    void addDiscount(Integer productId, Integer amount, LocalDateTime endDate);

    void deleteDiscount(Integer productId);
}
