package online_market.app.comments_and_shopping_cart_service.controllers;

import lombok.RequiredArgsConstructor;
import online_market.app.comments_and_shopping_cart_service.entity.ProductFromCart;
import online_market.app.comments_and_shopping_cart_service.payload.NewProductFromCartPayload;
import online_market.app.comments_and_shopping_cart_service.service.ProductFromCartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/products-from-cart")
@RequiredArgsConstructor
public class ProductsFromCartRestController {

    private final ProductFromCartService productFromCartService;

    @GetMapping("{username}")
    public List<ProductFromCart> getAllProductsFromUserCart(@PathVariable("username") String userName) {
        return this.productFromCartService.findAllProductsFromUserCart(userName);
    }

    @PostMapping
    public ResponseEntity<?> addProductToUserCart(@RequestBody NewProductFromCartPayload payload,
                                                  UriComponentsBuilder uriBuilder) {
        try {
            ProductFromCart product = this.productFromCartService.addProductToCart(payload.productId(), payload.username());
            return ResponseEntity.created(uriBuilder
                            .replacePath("api/products-from-cart/products/{productId}/user/{username}")
                            .build(Map.of("productId", payload.productId(),"username",payload.username())))
                    .body(product);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("products/{productId}/user/{username}")
    public ResponseEntity<?> deleteProductFromUserCart(
            @PathVariable("productId") Integer productId,
            @PathVariable("username") String userName
    ){
        try {
            this.productFromCartService.deleteProductFromCart(productId, userName);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("products/{productId}/user/{username}")
    public ResponseEntity<?> getProductFromUserCart(
            @PathVariable("productId") Integer productId,
            @PathVariable("username") String userName,
            UriComponentsBuilder uriBuilder){
        try {
            ProductFromCart productFromCart = this.productFromCartService.findProductFromCartByUserNameAndProductId(userName,productId);
            return ResponseEntity.created(uriBuilder
                            .replacePath("api/products-from-cart/products/{productId}/user/{username}")
                            .build(Map.of("productId", productId,"username",userName)))
                    .body(productFromCart);
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }
}
