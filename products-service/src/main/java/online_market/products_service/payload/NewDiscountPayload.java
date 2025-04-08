package online_market.products_service.payload;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record NewDiscountPayload(

        @Min(value = 1, message = "минимальный размер скидки 1%")
        @Max(value = 100,message = "максимальная скидка 100%")
        Integer amount,

        LocalDateTime endDiscount
) {
}
