package online_market.order_and_notification_service.payload;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.NonNull;

import java.math.BigDecimal;

public record NewOrderPayload(

        String sellerUsername,

        String sellerEmail,

        String productTitle,

        Integer count,

        String buyerUsername,

        @NotNull(message = "Ошибка отправки запроса, Id товара равен 0")
        Integer productId,

    @Email(message = "Email указан неверно!")
    String buyerEmail,

    @Size(min = 5, max = 150, message = "Слишком длинные или короткие контактные данные")
    String buyerDetail,

        @Size(min = 30, max = 150, message = "Слишком длинные или короткий адрес")
    String address,

    String postcode,

    BigDecimal amount,

    String paymentType
)
{}
