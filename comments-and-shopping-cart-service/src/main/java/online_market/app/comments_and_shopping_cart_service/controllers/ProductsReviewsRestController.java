package online_market.app.comments_and_shopping_cart_service.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import online_market.app.comments_and_shopping_cart_service.entity.ProductsReview;
import online_market.app.comments_and_shopping_cart_service.payload.NewProductReviewPayload;
import online_market.app.comments_and_shopping_cart_service.payload.UpdateProductReviewPayload;
import online_market.app.comments_and_shopping_cart_service.service.ProductReviewService;
import org.apache.coyote.BadRequestException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/products-review")
@RequiredArgsConstructor
public class ProductsReviewsRestController {

    private final ProductReviewService productReviewService;

    @GetMapping("{name-media}")
    public ResponseEntity<?> findMediaByName(@PathVariable("name-media") String mediaName) throws IOException {
        Resource media = this.productReviewService.getMedia(mediaName);
        String contentType = Files.probeContentType(Paths.get(media.getFile().getAbsolutePath()));
        InputStreamResource resource = new InputStreamResource(media.getInputStream());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    @DeleteMapping("/{reviewId}/{media-name}")
    public ResponseEntity<?> deleteMediaByName(@PathVariable("media-name") String mediaName,
                                               @PathVariable("reviewId") String reviewId) throws IOException {
        try {
            this.productReviewService.deleteMedia(mediaName, reviewId);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

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

    @PostMapping("/media/{reviewId}")
    public ResponseEntity<?> addMedia(@PathVariable("reviewId") String reviewId,
                                      @RequestParam("media") MultipartFile media) throws IOException {
        try {
            this.productReviewService.addMedias(media, reviewId);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/medias/{reviewId}")
    public ResponseEntity<?> addMedias(@RequestPart("files") List<MultipartFile> medias,
                                      @PathVariable("reviewId") String reviewId){
        try {
            System.out.println(medias.size());
            this.productReviewService.addMedias(medias, reviewId);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        } catch (BadRequestException exception){
            return ResponseEntity.badRequest().body(exception);
        }

    }

    @PatchMapping
    public ResponseEntity<?> editProductReview(@Valid @RequestBody UpdateProductReviewPayload payload,
                                               BindingResult bindingResult) throws BindException {
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
    ) throws IOException{
        try{
            this.productReviewService.deleteProductReview(username, productId);
            return ResponseEntity.noContent().build();
        }catch (NoSuchElementException exception){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/product/{productId}")
    public List<ProductsReview> getAllReviewsOfProduct(@PathVariable("productId") Integer productId) {
        return this.productReviewService.getAllReviewsByProductId(productId);
    }
}
