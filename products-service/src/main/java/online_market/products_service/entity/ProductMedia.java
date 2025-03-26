package online_market.products_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "products_schema", name = "t_product_media")
public class ProductMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "c_media_path")
    private String mediaPath;

    @Column(name = "c_media_type")
    private String mediaType;

    @ManyToOne
    @JoinColumn(name = "c_product_id", nullable = false)
    private Product product;
}
