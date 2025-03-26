package online_market.user_app.client.productFromCart;

import online_market.user_app.entity.ProductFromCart;

import java.util.List;

public interface ProductFromUserCartClient {

    List<ProductFromCart> getAllProductsFromUserCart(String username);

    ProductFromCart addProductFromUserCart(String username, Integer productId);

    void deleteProductFromUserCart(String username, Integer productId);

    ProductFromCart getProductFromUserCart(String username, Integer productId);
}