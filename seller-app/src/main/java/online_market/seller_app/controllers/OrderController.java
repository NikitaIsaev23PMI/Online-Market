package online_market.seller_app.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import online_market.seller_app.client.order.OrderRestClient;
import online_market.seller_app.enums.OrderStatus;
import online_market.seller_app.payload.UpdateOrderPayload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("online-market/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRestClient orderRestClient;

    @GetMapping("my-orders")
    public String myOrders(Model model, @AuthenticationPrincipal OidcUser user) {
        model.addAttribute("orders", this.orderRestClient.getSellerOrders(user.getPreferredUsername()));
        return "orders/my-orders";
    }

    @GetMapping("{orderId}")
    public String myOrder(@PathVariable("orderId") Integer orderId,
                          Model model) {
        try {
            model.addAttribute("statuses", OrderStatus.values());
            model.addAttribute("order", this.orderRestClient.getSellerOrder(orderId));
            return "orders/my-order";
        } catch (NoSuchElementException e){
            return "errors/404";
        }
    }

    @PostMapping("/edit/{orderId}")
    public String setTimeOfDeliveryAndStatusForOrder(@PathVariable("orderId") Integer orderId,
                                                     UpdateOrderPayload updateOrderPayload,
                                                     HttpServletRequest request){
        try {
            this.orderRestClient.updateOrderStatusAndTimeOfDelivery(updateOrderPayload.status(),
                    updateOrderPayload.timeOfDelivery(), orderId);
            return "redirect:" + request.getHeader("referer");
        } catch (NoSuchElementException e){
            return "errors/404";
        }
    }
}
