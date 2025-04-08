package online_market.seller_app.client;

import online_market.seller_app.entity.Product;
import online_market.seller_app.entity.ProductMedia;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductRestClient {

    List<Product> findAllProducts(String filter);

    Product createProduct(String title, String details, String sellerSubject, BigDecimal price);

    void updateProduct(int id, String title, String details, String sellerSubject, BigDecimal price);

    Optional<Product> findProduct(int id);

    void deleteProduct(int id);

    List<Product> getSellerProducts(String sellerSubject);

}
