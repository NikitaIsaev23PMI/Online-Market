package online_market.app.comments_and_shopping_cart_service.controllers;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import online_market.app.comments_and_shopping_cart_service.entity.ProductsReview;
import online_market.app.comments_and_shopping_cart_service.payload.NewProductReviewPayload;
import online_market.app.comments_and_shopping_cart_service.payload.UpdateProductReviewPayload;
import online_market.app.comments_and_shopping_cart_service.service.ProductReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/products-review")
@RequiredArgsConstructor
public class ProductsReviewsRestController {

    private final ProductReviewService productReviewService;

    @GetMapping("/product/{productId}/user/{username}")
    public ResponseEntity<?> getProductReview(@PathVariable("productId") Integer productId,
                                              @PathVariable("username") String username,
                                              UriComponentsBuilder uriBuilder) {
        try {
            ProductsReview review = this.productReviewService.getProductReview(username, productId);
            return ResponseEntity.created(uriBuilder.replacePath("api/products-review/product/{productId}/user/{username}")
                    .build(Map.of("productId",productId, "username", username)))
                    .body(review);
        }catch (NoSuchElementException exception){
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<?> addProductReview(NewProductReviewPayload payload, UriComponentsBuilder uriBuilder) {
        try {
          ProductsReview productsReview = this.productReviewService.addProductReview(
                  payload.userName(), payload.productId(),
                  payload.Review(), payload.rating());
          return ResponseEntity.created(uriBuilder.replacePath("api/products-review/product/{productId}/user/{username}")
                  .build(Map.of("productId",payload.productId(), "username", payload.userName())))
                  .body(productsReview);
        } catch (IllegalStateException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PatchMapping
    public ResponseEntity<?> editProductReview(UpdateProductReviewPayload payload) {
        try {
            this.productReviewService.editProductReview(payload.userName(),payload.productId(),
                    payload.Review(),payload.rating());
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException exception){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/product/{productId}/user/{username}")
    public ResponseEntity<?> deleteProductReview(
            @PathVariable("productId") Integer productId,
            @PathVariable("username") String username
    ){
        try{
            this.productReviewService.deleteProductReview(username, productId);
            return ResponseEntity.noContent().build();
        }catch (NoSuchElementException exception){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{productId}")
    public List<ProductsReview> getAllReviewsOfProduct(@PathVariable("productId") Integer productId) {
        return this.productReviewService.getAllReviewsByProductId(productId);
    }
}
