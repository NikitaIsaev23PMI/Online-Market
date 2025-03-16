package online_market.products_service.controllers;

import lombok.RequiredArgsConstructor;
import online_market.products_service.entity.Product;
import online_market.products_service.payload.NewProductPayload;
import online_market.products_service.payload.UpdateProductPayload;
import online_market.products_service.repository.ProductRepository;
import online_market.products_service.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("products-service-api/products")
@RequiredArgsConstructor
public class ProductsRestController {

    private final ProductService productService;

    @GetMapping()
    public List<Product> getProducts() {
        return this.productService.findAllProduct();
    }

    @GetMapping("{productId}")
    public Product findProduct(@PathVariable("productId") int productId) {
        return this.productService.findById(productId);
    }

    @PostMapping()
    public ResponseEntity<Product> createProduct(@RequestBody NewProductPayload payload,
                                                 UriComponentsBuilder uriBuilder) {
        Product product = this.productService.create(payload.title(), payload.details());

        return ResponseEntity.created(uriBuilder
                .replacePath("products-service-api/products/{productId}")
                .build(Map.of("productId",product.getId())))
                .body(product);
    }

    @PatchMapping("{productId}")
    public ResponseEntity<?> updateProduct(@RequestBody UpdateProductPayload payload,
                                                 @PathVariable("productId") int productId) {
        this.productService.updateProduct(productId,payload.title(),payload.details());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") int productId){
        this.productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
