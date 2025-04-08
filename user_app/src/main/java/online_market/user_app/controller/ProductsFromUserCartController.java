package online_market.user_app.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import online_market.user_app.client.product.ProductRestClient;
import online_market.user_app.client.productFromCart.ProductFromUserCartClient;
import online_market.user_app.entity.Product;
import online_market.user_app.entity.ProductFromCart;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("online-market/buyer/products-from-cart")
@RequiredArgsConstructor
public class ProductsFromUserCartController {

    private final ProductFromUserCartClient productFromUserCartClient;

    private final ProductRestClient productRestClient;

    @GetMapping()
    public String getUserProductsCartPage(@AuthenticationPrincipal OidcUser principal,
                                          Model model) {

        List<Integer> productsFromCart = this.productFromUserCartClient.
                getAllProductsFromUserCart(principal.getPreferredUsername()).stream()
                .map(ProductFromCart::getProductId).toList();

        model.addAttribute("products", productRestClient.findProductsByListOfId(productsFromCart));
        return "users/cart";
    }

    @PostMapping("add/{productId}")
    public String addProductInCart(@PathVariable Integer productId,
                                 @AuthenticationPrincipal OidcUser principal) {
        this.productFromUserCartClient.
                addProductFromUserCart(principal.getPreferredUsername(), productId);
        return "redirect:/online-market/buyer/products/%d".formatted(productId);
    }

    @PostMapping("delete/{productId}")
    public String deleteProductFromCart(@PathVariable Integer productId,
                                      @AuthenticationPrincipal OidcUser principal,
                                        HttpServletRequest request) {
        this.productFromUserCartClient.
                deleteProductFromUserCart(principal.getPreferredUsername(), productId);
        return "redirect:" + request.getHeader("Referer");
    }
}
