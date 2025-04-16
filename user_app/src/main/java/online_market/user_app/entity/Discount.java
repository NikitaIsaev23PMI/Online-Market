package online_market.user_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Discount {

    private Product product;

    private Integer amount;

    private LocalDateTime endDate;
}
