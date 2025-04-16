package online_market.products_service.services.product;

import online_market.products_service.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface ProductService {

    List<Product> findAllProduct(String filter, String category);

    Product findById(int id);

    Product create(String title,String details, String sellerSubject, BigDecimal price,String category);

    void updateProduct(int id, String title, String details, String sellerSubject, BigDecimal price, String category);

    void deleteProduct(int id, String sellerSubject);

    List<Product> findProductsBySellerSubject(String sellerSubject);

    Product findProductBySellerSubject(String sellerSubject);

    List<Product> findProductsByListIds(List<Integer> listOfId);
}
