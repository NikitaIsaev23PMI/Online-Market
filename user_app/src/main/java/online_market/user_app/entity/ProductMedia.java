package online_market.user_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductMedia {

    private Integer id;

    private String mediaPath;

    private String mediaType;

    private Product product;
}
