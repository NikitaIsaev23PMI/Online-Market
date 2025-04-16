package online_market.products_service.repository;

import jakarta.transaction.Transactional;
import online_market.products_service.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiscountRepository extends JpaRepository<Discount, Integer> {

    @Modifying
    @Query("DELETE FROM Discount d WHERE d.product.id = :productId")
    @Transactional
    void deleteDiscountByProductId(@Param("productId") Integer productId);
}
