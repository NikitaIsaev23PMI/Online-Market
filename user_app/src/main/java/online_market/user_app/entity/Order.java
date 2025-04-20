package online_market.user_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Integer id;

    private LocalDateTime timeOfCreated;

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

