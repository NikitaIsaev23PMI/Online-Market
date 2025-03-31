package online_market.seller_app.entity;

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
}
