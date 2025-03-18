package online_market.products_service.repository;

import online_market.products_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByTitleContainsIgnoreCase(String filter);

    Optional<Product> findById(int id);

    Product save(Product product);

    List<Product> findAllBySellerUserName(String sellerUserName);
}
