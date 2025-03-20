package online_market.products_service.services;

import online_market.products_service.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAllProduct(String filter);

    Product findById(int id);

    Product create(String title,String details, String sellerSubject);

    void updateProduct(int id, String title, String details, String sellerSubject);

    void deleteProduct(int id, String sellerSubject);

    List<Product> findProductsBySellerSubject(String sellerSubject);

    Product findProductBySellerSubject(String sellerSubject);
}
