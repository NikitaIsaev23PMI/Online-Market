package online_market.user_app.controller;

import lombok.RequiredArgsConstructor;
import online_market.user_app.client.product.ProductRestClient;
import online_market.user_app.client.productFromCart.ProductFromUserCartClient;
import online_market.user_app.client.productReview.ProductReviewRestClient;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("online-market/buyer/products")
public class ProductsController {

    private final ProductRestClient productRestClient;

    @GetMapping()
    public String getProductsPage(@RequestParam(name = "filter", required = false) String filter, Model model,
                                  @AuthenticationPrincipal OidcUser principal) {
        model.addAttribute("products", this.productRestClient.getAllProduct(filter));
        return "products/list";
    }

    @GetMapping("{productId}")
    public String getProductPage(@PathVariable("productId") int productId, Model model) {
        model.addAttribute("product", this.productRestClient.getProduct(productId));
        return "products/product";
    }

}
