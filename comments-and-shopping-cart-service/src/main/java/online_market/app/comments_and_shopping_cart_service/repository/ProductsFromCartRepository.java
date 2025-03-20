package online_market.app.comments_and_shopping_cart_service.repository;

import online_market.app.comments_and_shopping_cart_service.entity.ProductFromCart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsFromCartRepository extends MongoRepository<ProductFromCart,String> {

    List<ProductFromCart> findAllByUserName(String userName);

    ProductFromCart save(ProductFromCart productFromCart);

    void deleteByUserNameAndProductId(String userName, Integer productId);

    Optional<ProductFromCart> findProductFromCartByUserNameAndProductId(String userName, Integer productId);

}
