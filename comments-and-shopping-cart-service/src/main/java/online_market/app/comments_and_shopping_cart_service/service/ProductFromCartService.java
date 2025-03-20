package online_market.app.comments_and_shopping_cart_service.service;

import online_market.app.comments_and_shopping_cart_service.entity.ProductFromCart;

import java.util.List;
import java.util.Optional;

public interface ProductFromCartService {

    ProductFromCart addProductToCart(Integer productId, String userName);

    List<ProductFromCart> findAllProductsFromUserCart(String userName);

    void deleteProductFromCart(Integer productId, String userName);

    Optional<ProductFromCart> findProductFromCartByUserNameAndProductId(String userName, Integer productId);
}
