package online_market.seller_app.client;

import online_market.seller_app.payload.NewDiscountPayload;

import java.time.LocalDateTime;

public interface DiscountRestClient {

    void SetDiscountForProduct(Integer productId, Integer amount, LocalDateTime endDiscount);

    void deleteDiscount(Integer productId);
}
