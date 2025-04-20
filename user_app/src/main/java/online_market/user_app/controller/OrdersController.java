package online_market.user_app.controller;

import lombok.RequiredArgsConstructor;
import online_market.user_app.client.order.OrderRestClient;
import online_market.user_app.entity.Product;
import online_market.user_app.payload.NewOrderPayload;
import org.apache.catalina.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("online-market/buyer/order")
@RequiredArgsConstructor
public class OrdersController {

    private final OrderRestClient orderRestClient;

    @GetMapping("/new")
    public String newOrder(Product product, Model model,
                           @AuthenticationPrincipal OidcUser user) {
        model.addAttribute("product", product);
        model.addAttribute("buyer",user);
        return "orders/new";
    }

    @PostMapping()
    public String createNewOrder(NewOrderPayload payload){
        this.orderRestClient.newOrder()  //TODO
    }
}
