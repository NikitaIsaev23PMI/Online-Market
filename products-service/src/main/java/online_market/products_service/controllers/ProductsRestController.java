package online_market.products_service.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import online_market.products_service.entity.Product;
import online_market.products_service.payload.NewProductPayload;
import online_market.products_service.payload.UpdateProductPayload;
import online_market.products_service.repository.ProductRepository;
import online_market.products_service.services.ProductService;
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
@RequestMapping("products-service-api/products")
@RequiredArgsConstructor
public class ProductsRestController {

    private final ProductService productService;

    @GetMapping()
    public List<Product> getAllProducts(@RequestParam(name = "filter", required = false) String filter) {
        return this.productService.findAllProduct(filter);
    }

    @GetMapping("{productId}")
    public Product findProduct(@PathVariable("productId") int productId){
        return this.productService.findById(productId);
    }

    @PostMapping()
    public ResponseEntity<Product> createProduct(@Valid @RequestBody NewProductPayload payload,
                                                 UriComponentsBuilder uriBuilder,
                                                 BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if(bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            Product product = this.productService.create(payload.title(), payload.details());

            return ResponseEntity.created(uriBuilder
                            .replacePath("products-service-api/products/{productId}")
                            .build(Map.of("productId", product.getId())))
                    .body(product);
        }
    }

    @PatchMapping("{productId}")
    public ResponseEntity<?> updateProduct(@Valid @RequestBody UpdateProductPayload payload,
                                                 @PathVariable("productId") int productId,
                                           BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            this.productService.updateProduct(productId, payload.title(), payload.details());
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") int productId){
        this.productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
