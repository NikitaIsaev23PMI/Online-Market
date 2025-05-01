package online_market.user_app.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online_market.user_app.client.exception.CreatingOrderException;
import online_market.user_app.client.order.OrderRestClient;
import online_market.user_app.client.product.ProductRestClient;
import online_market.user_app.entity.Order;
import online_market.user_app.entity.Product;
import online_market.user_app.payload.NewOrderPayload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.NoSuchElementException;

@Slf4j
@Controller
@RequestMapping("online-market/buyer/order")
@RequiredArgsConstructor
public class OrdersController {

    private final OrderRestClient orderRestClient;

    private final ProductRestClient productRestClient;

    @GetMapping("/new")
    public String newOrder(@RequestParam Integer productId,
                           Model model,
                           @AuthenticationPrincipal OidcUser user) {

        Product product = productRestClient.getProduct(productId);
        NewOrderPayload payload = new NewOrderPayload();

        payload.setSellerUsername(product.getSeller().getPreferredUsername());
        payload.setSellerEmail(product.getSeller().getEmail());
        payload.setProductId(product.getId());
        payload.setProductTitle(product.getTitle());
        payload.setBuyerUsername(user.getPreferredUsername());
        payload.setBuyerEmail(user.getEmail());
        payload.setAmount(product.getPrice());

        model.addAttribute("product", product);
        model.addAttribute("seller", product.getSeller());
        model.addAttribute("buyer", user);
        model.addAttribute("payload", payload);

        return "users/order";
    }

    @PostMapping()
    public String createNewOrder(NewOrderPayload payload, RedirectAttributes redirectAttributes,
                                 HttpServletRequest request) {
        try {
            Order order = this.orderRestClient.newOrder(payload.getSellerUsername(), payload.getSellerEmail(), payload.getProductTitle(),
                    payload.getCount(), payload.getBuyerUsername(), payload.getProductId(), payload.getBuyerEmail(), payload.getBuyerDetail(),
                    payload.getAddress(), payload.getPostcode(), payload.getAmount(), payload.getPaymentType());
            return "redirect:/online-market/buyer/order/%d".formatted(order.getId());
        } catch (CreatingOrderException exception) {
            redirectAttributes.addFlashAttribute("errors", exception.getErrors());
            return "redirect:" + request.getHeader("referer");
        }
    }

    @GetMapping("/{orderId}")
    public String getOrderPage(@PathVariable("orderId") Integer orderId, Model model) {
        try {
            Order order = this.orderRestClient.getUserOrder(orderId);
            model.addAttribute("order", order);
            return "users/order-page";
        } catch (NoSuchElementException exception) {
            model.addAttribute("error", exception.getMessage());
            return "errors/error";
        }
    }

    @PostMapping("/delete/{orderId}")
    public String deleteOrder(@PathVariable("orderId") Integer orderId,
                              HttpServletRequest request) {
            this.orderRestClient.deleteOrder(orderId);
            return "redirect:" + request.getHeader("referer");
    }

    @GetMapping("/my-orders")
    public String getBuyersOrdersPage(Model model, @AuthenticationPrincipal OidcUser user) {
        model.addAttribute("orders", this.orderRestClient.getAllUserOrders(user.getPreferredUsername()));
        return "users/my-orders";
    }
}
