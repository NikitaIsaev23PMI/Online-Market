package online_market.user_app.payload;


import java.math.BigDecimal;

public record NewOrderPayload(

        String sellerUsername,

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

        String paymentType
)
{}
