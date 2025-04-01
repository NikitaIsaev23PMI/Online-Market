package online_market.app.comments_and_shopping_cart_service.repository;

import online_market.app.comments_and_shopping_cart_service.entity.ProductsReview;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsReviewRepository extends MongoRepository<ProductsReview, String> {

    Optional<ProductsReview> getProductByUserNameAndProductId(String userName, Integer productId);

    List<ProductsReview> findAllByProductId(Integer productId);
}
