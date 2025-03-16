package online_market.products_service.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewProductPayload(

        @NotNull(message = "Название товара не может быть пустым")
        @Size(min = 3,max = 100, message = "Название товара должно находиться в диапазоне от 3 до 100 символов")
        String title,

        @NotNull(message = "Описание товара не может быть пустым")
        @Size(min = 5,max = 1000, message = "Описание товара должно находиться в диапазоне от 5 до 1000 символов")
        String details
)
{}

