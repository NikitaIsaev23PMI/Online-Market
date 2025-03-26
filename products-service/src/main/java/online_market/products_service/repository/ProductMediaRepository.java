package online_market.products_service.repository;

import online_market.products_service.entity.ProductMedia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductMediaRepository extends JpaRepository<ProductMedia, Integer> {
    
    ProductMedia findByMediaPath(ProductMedia media);

}
