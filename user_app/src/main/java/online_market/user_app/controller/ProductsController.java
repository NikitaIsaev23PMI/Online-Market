package online_market.user_app.controller;

import lombok.RequiredArgsConstructor;
import online_market.user_app.client.product.ProductRestClient;
import online_market.user_app.client.productFromCart.ProductFromUserCartClient;
import online_market.user_app.client.productReview.ProductReviewRestClient;
import online_market.user_app.entity.Product;
import online_market.user_app.enums.Category;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("online-market/buyer/products")
public class ProductsController {

    private final ProductRestClient productRestClient;

    private final ProductReviewRestClient productReviewRestClient;

    private final ProductFromUserCartClient productFromUserCartClient;

    @GetMapping()
    public String getProductsPage(@RequestParam(name = "filter", required = false) String filter,
                                  @RequestParam(name = "category", required = false) String category,
                                  Model model) {
        List<Product> products = this.productRestClient.getAllProduct(filter,category);
        model.addAttribute("products", products);
        model.addAttribute("filter", filter);
        model.addAttribute("filterCategory", category);
        model.addAttribute("categories", Arrays.stream(Category.values()).toList());
        return "products/list";
    }

    @GetMapping("{productId}")
    public String getProductPage(@PathVariable("productId") int productId, Model model,
                                 @AuthenticationPrincipal OidcUser principal) {
        model.addAttribute("product", this.productRestClient.getProduct(productId));
        model.addAttribute("productReviews",
                this.productReviewRestClient.getAllReviewsOfProduct(productId));
//        System.out.println(this.productReviewRestClient.getAllReviewsOfProduct(productId).getFirst().getMedias().getFirst());
        model.addAttribute("user", principal);
        model.addAttribute("IsInCart",
                this.productFromUserCartClient.productIsInUserCart(principal.getPreferredUsername(),productId));
        return "products/product";
    }

}
