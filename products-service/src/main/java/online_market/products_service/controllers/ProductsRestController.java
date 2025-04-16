package online_market.products_service.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import online_market.products_service.entity.Product;
import online_market.products_service.payload.NewDiscountPayload;
import online_market.products_service.payload.NewProductPayload;
import online_market.products_service.payload.UpdateProductPayload;
import online_market.products_service.services.discount.DiscountService;
import online_market.products_service.services.product.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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
    public List<Product> getAllProducts(@RequestParam(name = "filter", required = false) String filter,
                                        @RequestParam(name = "category", required = false) String category) {
        return this.productService.findAllProduct(filter, category);
    }

    @GetMapping("product/{productId}")
    public ResponseEntity<?> findProduct(@PathVariable("productId") int productId,
                                         UriComponentsBuilder uriBuilder){
        try {
            Product product = this.productService.findById(productId);
            return ResponseEntity.created(uriBuilder
                            .replacePath("products-service-api/products/{productId}")
                            .build(Map.of("productId", product.getId())))
                    .body(product);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{sub}")
    public List<Product> findProductsByUsername(@PathVariable("sub") String sellerSubject){
            return this.productService.findProductsBySellerSubject(sellerSubject);
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
                Product product = this.productService.create(payload.title(), payload.details(),
                        payload.sellerSubject(), payload.price(), payload.category());
                return ResponseEntity.created(uriBuilder
                                .replacePath("products-service-api/products/{productId}")
                                .build(Map.of("productId", product.getId())))
                        .body(product);
        }
    }

    @PatchMapping("{productId}")
    public ResponseEntity<?> updateProduct(@Valid @RequestBody UpdateProductPayload payload,
                                                 @PathVariable("productId") int productId,
                                           BindingResult bindingResult,
                                           JwtAuthenticationToken jwt) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            try {
                this.productService.updateProduct(productId, payload.title(),
                        payload.details(),
                        jwt.getToken().getSubject(), payload.price(), payload.category());
                return ResponseEntity.noContent().build();
            } catch (AccessDeniedException e) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            } catch (NoSuchElementException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
    }

    @DeleteMapping("{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") int productId,
                                           JwtAuthenticationToken token) {
        try {
            this.productService.deleteProduct(productId,token.getToken().getSubject());
            return ResponseEntity.noContent().build();
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("products-by-list-id")
    public List<Product> findProductsByListId(@RequestBody List<Integer> listOfId){
        return this.productService.findProductsByListIds(listOfId);
    }
}

