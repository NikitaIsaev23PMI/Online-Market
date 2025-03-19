package online_market.user_app.client;

import online_market.user_app.entity.Product;

import java.util.List;

public interface ProductRestClient {

    List<Product> getAllProduct(String filter);

    Product getProduct(int id);


}
