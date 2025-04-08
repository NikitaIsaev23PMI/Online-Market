package online_market.user_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private int id;

    private String title;

    private String details;

    private List<ProductMedia> productMedia;

    private BigDecimal price;
}
