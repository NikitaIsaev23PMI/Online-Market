package online_market.seller_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {

    private int id;

    private String title;

    private String details;

    private Seller seller;

    private BigDecimal price;

    private List<ProductMedia> productMedia;

    private Discount discount;

    private String category;

    private Integer count;
}
