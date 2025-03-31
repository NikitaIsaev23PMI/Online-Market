package online_market.seller_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {

    private int id;

    private String title;

    private String details;

    private String sellerSubject;

    private List<ProductMedia> productMedia;
}
