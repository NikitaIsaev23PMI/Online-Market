package online_market.seller_app.client;

import online_market.seller_app.entity.ProductMedia;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductMediaRestClient {

    ProductMedia addMedia(MultipartFile file, Integer productId) ;

    List<ProductMedia> getProductMedia(int productId);

    void deleteProductMedia(Integer mediaId);
}
