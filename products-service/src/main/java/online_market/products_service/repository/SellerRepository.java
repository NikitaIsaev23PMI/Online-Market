package online_market.products_service.repository;

import online_market.products_service.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Integer> {

    Optional<Seller> findSellerBySubject(String subject);
}
