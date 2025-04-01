package online_market.user_app.controller;

import lombok.RequiredArgsConstructor;
import online_market.user_app.client.productFromCart.ProductFromUserCartClient;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("online-market/buyer/products-from-cart")
@RequiredArgsConstructor
public class ProductsFromUserCartController {

    private final ProductFromUserCartClient productFromUserCartClient;

    @GetMapping()
    public String getUserProductsCartPage(@AuthenticationPrincipal OidcUser principal, Model model) {
        model.addAttribute("products",
                this.productFromUserCartClient.getAllProductsFromUserCart(principal.getPreferredUsername()));
        return "users/cart";
    }

    @PostMapping("add/{productId}")
    public void addProductInCart(@PathVariable Integer productId,
                                 @AuthenticationPrincipal OidcUser principal) {
        this.productFromUserCartClient.
                addProductFromUserCart(principal.getPreferredUsername(), productId);
    }

    @PostMapping("delete/{productId}")
    public void deleteProductFromCart(@PathVariable Integer productId,
                                      @AuthenticationPrincipal OidcUser principal) {
        this.productFromUserCartClient.
                deleteProductFromUserCart(principal.getPreferredUsername(), productId);
    }
}
