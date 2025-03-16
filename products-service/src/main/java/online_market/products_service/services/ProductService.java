package online_market.products_service.services;

import online_market.products_service.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAllProduct();

    Product findById(int id);

    Product create(String title,String details);

    void updateProduct(int id, String title, String details);

    void deleteProduct(int id);
}
