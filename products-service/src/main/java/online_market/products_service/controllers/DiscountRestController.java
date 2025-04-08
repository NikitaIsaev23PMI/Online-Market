package online_market.products_service.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import online_market.products_service.entity.Product;
import online_market.products_service.payload.NewDiscountPayload;
import online_market.products_service.services.discount.DiscountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("products-service-api/products/discount")
@RequiredArgsConstructor
public class DiscountRestController {

    private final DiscountService discountService;

    @PostMapping("{productId}")
    public ResponseEntity<?> addDiscount(@PathVariable("productId") int productId,
                                         @Valid @RequestBody NewDiscountPayload newDiscountPayload,
                                         BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if(bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            try {
                this.discountService.addDiscount(productId,newDiscountPayload.amount(),newDiscountPayload.endDiscount());
                return ResponseEntity.noContent().build();
            } catch (NoSuchElementException exception) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
    }

    @DeleteMapping("{productId}")
    public ResponseEntity<?> deleteDiscount(@PathVariable("productId") Integer productId) {
        try {
            this.discountService.deleteDiscount(productId);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
