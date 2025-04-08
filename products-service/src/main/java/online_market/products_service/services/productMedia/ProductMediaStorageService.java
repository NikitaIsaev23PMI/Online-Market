package online_market.products_service.services.productMedia;

import online_market.products_service.entity.ProductMedia;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductMediaStorageService {

    ProductMedia addProductMedia(MultipartFile media, int productId) throws IOException;

    List<ProductMedia> findProductMediaByProductId(int productId);

    void deleteProductMedia(Integer mediaId) throws IOException;

    Resource getMedia(String mediaName) throws IOException;

}
