package online_market.app.comments_and_shopping_cart_service.service;

import lombok.RequiredArgsConstructor;

import online_market.app.comments_and_shopping_cart_service.entity.ProductsReview;
import online_market.app.comments_and_shopping_cart_service.repository.ProductsReviewRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
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
public class MainProductReviewService implements ProductReviewService {

    public final ProductsReviewRepository productsReviewRepository;

    @Value("${media.storage.location}")
    private String mediaDirectory;

    public Resource getMedia(String mediaName) throws IOException{
        Path filePath = Paths.get(mediaDirectory).resolve(mediaName);
        if (!Files.exists(filePath) || !Files.isReadable(filePath)) {
            throw new IOException("Файл не найден или недоступен: " + mediaName);
        }
        return new UrlResource(filePath.toUri());
    }

    @Override
    public void deleteMedia(String mediaName, String reviewId) throws IOException {
        String urlMedia = "http://localhost:8084/api/products-review/" + mediaName;
        Path filePath = Paths.get(mediaDirectory).resolve(mediaName);
        Files.deleteIfExists(filePath);
        this.productsReviewRepository.findById(reviewId).ifPresentOrElse(
                productsReview -> {
                    productsReview.getMedias().remove(urlMedia);
                    productsReviewRepository.save(productsReview);
                },() -> {throw new NoSuchElementException("отзыв не найден");}
        );
    }

    @Override
    public void addMedias(MultipartFile media, String reviewId) throws IOException {
        Path uploadPath = Paths.get(mediaDirectory);
        String fileName = UUID.randomUUID().toString() + "_" + media.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(media.getInputStream(), filePath);
        String MediaURL = "http://localhost:8084/api/products-review/" + fileName;
        this.productsReviewRepository.findById(reviewId).ifPresentOrElse(
                productsReview -> {
                    productsReview.getMedias().add(MediaURL);
                    this.productsReviewRepository.save(productsReview);
                }, () -> {throw new NoSuchElementException("отзыв не найден");}
        );
    }

    @Override
    public ProductsReview addProductReview(String username, Integer productId,
                                           String review, int rating){
        if(this.productsReviewRepository.getProductByUserNameAndProductId(username, productId).isPresent()){
            throw new IllegalStateException("Пользователь может оставить только один отзыв на товар");
        }
            return this.productsReviewRepository
                    .save(new ProductsReview(null, username, productId, review, rating, null));
    }


    @Override
    public void addMedias(List<MultipartFile> medias, String reviewId) throws BadRequestException {
        Path uploadPath = Paths.get(mediaDirectory);
        List<String> mediasURL = medias.stream().map(media -> {
            String fileName = UUID.randomUUID().toString() + "_" + media.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            try {
                Files.copy(media.getInputStream(), filePath);
            } catch (IOException e) {
                throw new IllegalStateException("ошибка");
            }
            return "http://localhost:8084/api/products-review/" + fileName;
        }).toList();
        this.productsReviewRepository.findById(reviewId).ifPresentOrElse(review -> {
        review.setMedias(mediasURL);
        this.productsReviewRepository.save(review);}, () -> {throw new NoSuchElementException("отзыв не найден");});
    }



    @Override
    public void editProductReview(String userName, Integer productId, String Review, int rating) {
        this.productsReviewRepository.getProductByUserNameAndProductId(userName, productId)
                .ifPresentOrElse(productReview -> {
                    productReview.setReview(Review);
                    productReview.setRating(rating);
                    productsReviewRepository.save(productReview);
                }, () -> new NoSuchElementException("Отзыв на товар не найден"));
    }

    @Override
    public void deleteProductReview(String userName, Integer productId) throws IOException {
        if (this.productsReviewRepository.getProductByUserNameAndProductId(userName, productId).isPresent()
        && this.productsReviewRepository.getProductByUserNameAndProductId(userName, productId).get().getMedias() != null) {
            for (String media : this.productsReviewRepository.getProductByUserNameAndProductId(userName, productId).get().getMedias()) {
                String mediaName = media.substring(media.lastIndexOf("/") + 1);
                Path filePath = Paths.get(mediaDirectory).resolve(mediaName);
                Files.deleteIfExists(filePath);
            }
        }
        this.productsReviewRepository.getProductByUserNameAndProductId(userName, productId)
                .ifPresentOrElse(productsReviewRepository::delete,
                        () -> new NoSuchElementException("Невозможно Удалить товар так как его не существует"));
    }

    @Override
    public ProductsReview getProductReview(String userName, Integer productId) {
            return this.productsReviewRepository.getProductByUserNameAndProductId(userName, productId).get();
    }

    @Override
    public List<ProductsReview> getAllReviewsByProductId(Integer productId) {
        return this.productsReviewRepository.findAllByProductId(productId);
    }

    @Override
    public List<ProductsReview> getAllReviews() {
        return this.productsReviewRepository.findAll();
    }
}
