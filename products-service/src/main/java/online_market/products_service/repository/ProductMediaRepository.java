package online_market.products_service.repository;

import online_market.products_service.entity.ProductMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductMediaRepository extends JpaRepository<ProductMedia, Integer> {
    
    Optional<ProductMedia> findByMediaPath(String mediaPath);

    void deleteProductMediaByMediaPath(String mediaPath);
}
