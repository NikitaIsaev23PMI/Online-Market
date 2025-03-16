package online_market.seller_app.client;

import online_market.seller_app.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRestClient {

    List<Product> findAllProducts();

    Product createProduct(String title, String details);

    void updateProduct(int id, String title, String details);

    Optional<Product> findProduct(String id);

    void deleteProduct(String id);

}
