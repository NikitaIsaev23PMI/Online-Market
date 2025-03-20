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

@RestController
@RequestMapping("api/products-from-cart")
@RequiredArgsConstructor
public class ProductsFromCartRestController {

    private final ProductFromCartService productFromCartService;

    @GetMapping("{userName}")
    public List<ProductFromCart> getAllProductsFromUserCart(@PathVariable("userName") String userName) {
        return this.productFromCartService.findAllProductsFromUserCart(userName);
    }


    @PostMapping
    public ResponseEntity<?> addProductToUserCart(@RequestBody NewProductFromCartPayload payload,
                                                  UriComponentsBuilder uriBuilder) {
        try {
            ProductFromCart product = this.productFromCartService.addProductToCart(payload.productId(), payload.userName());
            return ResponseEntity.created(uriBuilder
                            .replacePath("products-service-api/products/product/{productId}")
                            .build(Map.of("productId", payload.productId())))
                    .body(product);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
