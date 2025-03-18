package online_market.products_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(schema = "products_schema", name = "t_product")
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "c_title")
    @NotNull(message = "Название товара не может быть пустым")
    @Size(min = 3,max = 100, message = "Название товара должно находиться в диапазоне от 3 до 100 символов")
    private String title;

    @Column(name = "c_details")
    @NotNull(message = "Описание товара не может быть пустым")
    @Size(min = 5,max = 1000, message = "Описание товара должно находиться в диапазоне от 5 до 1000 символов")
    private String details;

    @Column(name = "seller_user_name")
    private String sellerUserName;

}
