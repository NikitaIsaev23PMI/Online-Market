package online_market.user_app.client.productFromCart;

import online_market.user_app.entity.ProductFromCart;

import java.util.List;

public interface ProductFromUserCartClient {

    List<ProductFromCart> getAllProductsFromUserCart(String username);

    ProductFromCart addProductFromUserCart(String userName, Integer productId);

    void deleteProductFromUserCart(String userName, Integer productId);

    ProductFromCart getProductFromUserCart(String userName, Integer productId);
}