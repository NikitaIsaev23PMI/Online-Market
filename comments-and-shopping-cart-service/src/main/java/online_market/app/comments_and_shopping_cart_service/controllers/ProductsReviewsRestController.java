package online_market.app.comments_and_shopping_cart_service.controllers;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import online_market.app.comments_and_shopping_cart_service.entity.ProductsReview;
import online_market.app.comments_and_shopping_cart_service.payload.NewProductReviewPayload;
import online_market.app.comments_and_shopping_cart_service.payload.UpdateProductReviewPayload;
import online_market.app.comments_and_shopping_cart_service.service.ProductReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
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

    @GetMapping("/all-review")
    public List<ProductsReview> getAllReviews() {
       return this.productReviewService.getAllReviews();
    }

    @GetMapping("/product/{productId}/user/{username}")
    public ResponseEntity<?> getProductReview(@PathVariable("productId") Integer productId,
                                              @PathVariable("username") String username,
                                              UriComponentsBuilder uriBuilder) {

            ProductsReview review = this.productReviewService.getProductReview(username, productId);
            return ResponseEntity.created(uriBuilder.replacePath("api/products-review/product/{productId}/user/{username}")
                    .build(Map.of("productId",productId, "username", username)))
                    .body(review);

    }


    @PostMapping()
    public ResponseEntity<?> addProductReview(@RequestBody @Valid NewProductReviewPayload payload,
                                              UriComponentsBuilder uriBuilder,
                                              BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            try {
                ProductsReview productsReview = this.productReviewService.addProductReview(
                        payload.username(), payload.productId(),
                        payload.review(), payload.rating());
                return ResponseEntity.created(uriBuilder.replacePath("api/products-review/product/{productId}/user/{username}")
                                .build(Map.of("productId", payload.productId(), "username", payload.username())))
                        .body(productsReview);
            } catch (IllegalStateException exception) {
                ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
                problemDetail.setProperty("errors", List.of(exception.getMessage()));
                return ResponseEntity.badRequest().body(problemDetail);
            }
        }
    }

    @PatchMapping
    public ResponseEntity<?> editProductReview(@Valid @RequestBody UpdateProductReviewPayload payload,
                                               BindingResult bindingResult) throws BindException {
        System.out.println(payload.toString());
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else{
                try {
                    this.productReviewService.editProductReview(payload.username(), payload.productId(),
                            payload.review(), payload.rating());
                    return ResponseEntity.noContent().build();
                } catch (NoSuchElementException exception) {
                    return ResponseEntity.notFound().build();
                }
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
