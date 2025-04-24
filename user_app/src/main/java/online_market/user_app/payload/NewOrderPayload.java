package online_market.user_app.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewOrderPayload{

        private String sellerUsername;

        private String sellerEmail;

    private String productTitle;

    private Integer count;

    private String buyerUsername;

    private Integer productId;

    private String buyerEmail;

    private String buyerDetail;

    private String address;

    private String postcode;

    private BigDecimal amount;

    private String paymentType;
}
