package online_market.order_and_notification_service.controllers;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import online_market.order_and_notification_service.entity.Order;
import online_market.order_and_notification_service.payload.NewOrderPayload;
import online_market.order_and_notification_service.payload.UpdateOrderPayload;
import online_market.order_and_notification_service.services.OrderService;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("order-api")
@RequiredArgsConstructor
public class OrdersRestController {

    private final OrderService orderService;

    @PostMapping("/new")
    public ResponseEntity<?> newOrder(@RequestBody @Valid NewOrderPayload payload,
                                      BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        }
            else{
                Order order = this.orderService.createOrder(payload.productId(),
                        payload.productTitle(), payload.count(),payload.sellerUsername(),
                        payload.sellerEmail(),
                        payload.buyerUsername(), payload.buyerEmail(),
                        payload.buyerDetail(), payload.address(), payload.postcode(), payload.amount(),
                        payload.paymentType());
                return ResponseEntity.ok(order);
            }
    }

    @GetMapping("/seller/{sellerUsername}")
    public List<Order> getAllSellerOrders(@PathVariable("sellerUsername") String sellerUsername) {
        return this.orderService.getAllSellerOrders(sellerUsername);
    }

    @GetMapping("/buyer/{buyerUsername}")
    public List<Order> getAllBuyerOrders(@PathVariable("buyerUsername") String buyerUsername) {
        return this.orderService.getAllBuyerOrders(buyerUsername);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable("orderId") Integer orderId) {
        try {
             Order order = this.orderService.getOrderById(orderId);
             return ResponseEntity.ok(order);
        } catch (NoSuchElementException exception){
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/edit/{orderId}")
    public ResponseEntity<?> setTimeOfDeliveryAndStatus(@PathVariable("orderId") Integer orderId,
                                                        @RequestBody UpdateOrderPayload payload) {
        try {
            this.orderService.updateOrder(payload.status(), payload.timeOfDelivery(), orderId);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException exception){
            return ResponseEntity.notFound().build();
        }
    }
}
