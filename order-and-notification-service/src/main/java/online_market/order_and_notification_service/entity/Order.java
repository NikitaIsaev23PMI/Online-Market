package online_market.order_and_notification_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online_market.order_and_notification_service.converters.OrderStatusConverter;
import online_market.order_and_notification_service.enums.OrderStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "t_order")
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "c_time_of_created")
    @CreationTimestamp
    private LocalDateTime timeOfCreated;

    @Column(name = "c_time_of_update")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "c_time_of_delivery")
    private LocalDateTime timeOfDelivered;

    @Column(name = "c_status")
    @Convert(converter = OrderStatusConverter.class)
    private OrderStatus status;

    @Column(name = "c_product_id")
    private Integer productId;

    @Column(name = "c_product_title")
    private String productTitle;

    @Column(name = "c_count")
    private Integer count;

    @Column(name = "c_seller_username")
    private String sellerUsername;

    @Column(name = "c_seller_email")
    private String sellerEmail;

    @Column(name = "c_buyer_username")
    private String buyerUsername;

    @Column(name = "c_buyer_email")
    private String buyerEmail;

    @Column(name = "c_buyer_detail")
    private String buyerDetail;

    @Column(name = "c_address")
    private String address;

    @Column(name = "c_postcode")
    private String postcode;

    @Column(name = "c_amount")
    private BigDecimal amount;

    @Column(name = "с_payment_type")
    private String paymentType;
}
