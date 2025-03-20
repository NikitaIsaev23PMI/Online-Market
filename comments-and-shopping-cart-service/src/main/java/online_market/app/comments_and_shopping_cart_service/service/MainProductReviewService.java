package online_market.app.comments_and_shopping_cart_service.service;

import lombok.RequiredArgsConstructor;
import online_market.app.comments_and_shopping_cart_service.entity.ProductsReview;
import online_market.app.comments_and_shopping_cart_service.repository.ProductsReviewRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MainProductReviewService implements ProductReviewService {

    public final ProductsReviewRepository productsReviewRepository;

    @Override
    public ProductsReview addProductReview(String userName, Integer productId,
                                           String Review, int rating) {
        if(this.productsReviewRepository.getProductByUserNameAndProductId(userName, productId).isPresent()){
            throw new IllegalStateException("Пользователь может оставить только один отзыв на товар");
        }
            return this.productsReviewRepository
                    .save(new ProductsReview(null, userName, productId, Review, rating));
    }

    @Override
    public void editProductReview(String userName, Integer productId, String Review, int rating) {
        this.productsReviewRepository.getProductByUserNameAndProductId(userName, productId)
                .ifPresentOrElse(productReview -> {
                    productReview.setReview(Review);
                    productReview.setRating(rating);
                }, () -> new NoSuchElementException("Отзыв на товар не найден"));
    }

    @Override
    public void deleteProductReview(String userName, Integer productId) {
        this.productsReviewRepository.getProductByUserNameAndProductId(userName, productId)
                .ifPresentOrElse(productsReviewRepository::delete,
                        () -> new NoSuchElementException("Невозможно Удалить товар так как его не существует"));
    }

    @Override
    public ProductsReview getProductReview(String userName, Integer productId) {
        if(this.productsReviewRepository.getProductByUserNameAndProductId(userName, productId).isPresent()){
            return this.productsReviewRepository.getProductByUserNameAndProductId(userName, productId).get();
        } else {
            throw new NoSuchElementException("Отзыв на товар не найден");
        }
    }

    @Override
    public List<ProductsReview> getAllReviewsByProductId(Integer productId) {
        return this.productsReviewRepository.findAllByProductId(productId);
    }
}
