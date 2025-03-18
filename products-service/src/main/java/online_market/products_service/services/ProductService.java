package online_market.products_service.services;

import online_market.products_service.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAllProduct(String filter);

    Product findById(int id);

    Product create(String title,String details, String sellerUserName);

    void updateProduct(int id, String title, String details);

    void deleteProduct(int id);

    List<Product> findProductsBySellerUserName(String sellerUserName);
}
