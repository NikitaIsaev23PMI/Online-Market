package online_market.products_service.services;

import lombok.RequiredArgsConstructor;
import online_market.products_service.entity.Product;
import online_market.products_service.entity.ProductMedia;
import online_market.products_service.repository.ProductMediaRepository;
import online_market.products_service.repository.ProductRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MainProductMediaStorageService implements ProductMediaStorageService {

    @Value("${media.storage.location}")
    private String MediaDirectory;

    private final ProductMediaRepository productMediaRepository;

    private final ProductRepository productRepository;

    private String saveMedia(MultipartFile media) throws IOException {

        Path uploadPath = Paths.get(MediaDirectory);
        String fileName = UUID.randomUUID().toString() + "_" + media.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(media.getInputStream(), filePath);

        return "http://localhost:8081/products-service-api/products-media/" + fileName;
    }

    @Override
    public Resource getMedia(String mediaName) throws IOException {
        Path filePath = Paths.get(MediaDirectory).resolve(mediaName);
        if (!Files.exists(filePath) || !Files.isReadable(filePath)) {
            throw new IOException("Файл не найден или недоступен: " + mediaName);
        }
        return new UrlResource(filePath.toUri());
    }

    @Override
    public ProductMedia addProductMedia(MultipartFile media, int productId) throws IOException {
        if (this.productRepository.findById(productId).isPresent() &&
                this.productRepository.findById(productId).get().getProductMedia().size() <= 5 ) {
            String filePath = saveMedia(media);
            String fileType = media.getContentType().startsWith("image") ? "image" : "video";
            Product product = this.productRepository.findById(productId).get();
            return productMediaRepository.save(new ProductMedia(null,filePath,fileType,product));
        } else throw new BadRequestException("К товару можно прикрепить не более 5 фото или видео!");
    }

    @Override
    public List<ProductMedia> findProductMediaByProductId(int productId) {
        return this.productRepository.findById(productId).orElseThrow(NoSuchElementException::new).getProductMedia();
    }

    @Override
    public void deleteProductMedia(Integer mediaId) throws IOException {
        if (this.productMediaRepository.findById(mediaId).isPresent()) {
                ProductMedia media = productMediaRepository.findById(mediaId).get();
                this.productMediaRepository.deleteById(mediaId);
                String mediaName = media.getMediaPath().substring(media.getMediaPath().lastIndexOf("/") + 1);
                Path filePath = Paths.get(MediaDirectory).resolve(mediaName);
                Files.deleteIfExists(filePath);
        } else throw new NoSuchElementException();
    }
}
