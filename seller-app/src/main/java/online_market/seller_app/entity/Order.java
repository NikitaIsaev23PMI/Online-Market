package online_market.seller_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online_market.seller_app.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Integer id;

    private LocalDateTime timeOfCreated;

    private LocalDateTime updatedAt;

    private LocalDateTime timeOfDelivered;

    private OrderStatus status;

    private Integer productId;

    private String productTitle;

    private Integer count;

    private String sellerUsername;

    private String sellerEmail;

    private String buyerUsername;

    private String buyerEmail;

    private String buyerDetail;

    private String address;

    private String postcode;

    private BigDecimal amount;

    private String paymentType;
}

