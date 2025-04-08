package online_market.products_service.entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(schema = "products_schema", name = "t_discount")
public class Discount implements Serializable {

    @Id
    private Integer id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(name = "c_amount")
    @Min(value = 1, message = "минимальный размер скидки 1%")
    @Max(value = 100,message = "максимальная скидка 100%")
    private Integer amount;

    @Column(name = "c_start")
    private LocalDateTime startDate;

    @Column(name = "c_end")
    private LocalDateTime endDate;

}
