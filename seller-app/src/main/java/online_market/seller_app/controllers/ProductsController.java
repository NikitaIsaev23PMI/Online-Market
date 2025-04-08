package online_market.seller_app.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import online_market.seller_app.client.exception.BadRequestException;
import online_market.seller_app.client.ProductRestClient;
import online_market.seller_app.entity.Product;
import online_market.seller_app.payload.NewProductPayload;
import online_market.seller_app.payload.UpdateProductPayload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("online-market/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductRestClient productRestClient;

    @GetMapping("list")
    public String listProducts(@RequestParam(name = "filter", required = false) String filter,
                               Model model, @AuthenticationPrincipal OidcUser user) {
        user.getAuthorities().stream().forEach(o -> System.out.println(o));
        model.addAttribute("products", this.productRestClient.findAllProducts(filter));
        model.addAttribute("filter", filter);
        return "products/list";
    }

    @GetMapping("myProducts")
    public String getSellerProducts(Model model, @AuthenticationPrincipal OidcUser user) {
        model.addAttribute("products", this.productRestClient.getSellerProducts(user.getSubject()));
        model.addAttribute("username", user.getName());
        return "products/seller-products";
    }

    @GetMapping("{productId}")
    public String getProductPage(Model model, @PathVariable("productId") int id,
                                 @AuthenticationPrincipal OidcUser user) {
        Product product = this.productRestClient.findProduct(id).get();
        model.addAttribute("product", product);

        if (product.getSellerSubject().equals(user.getSubject())) {
            return "products/my-product";
        }
        return "products/product";
    }

    @GetMapping("{productId}/edit")
    public String getProductEditPage(Model model, @PathVariable("productId") int id,
                                     @AuthenticationPrincipal OidcUser user) throws AccessDeniedException {
        Product product = this.productRestClient.findProduct(id).get();
        if (product.getSellerSubject().equals(user.getSubject())) {
            model.addAttribute("product", product);
            return "products/edit";
        } else throw new AccessDeniedException("Access denied");
    }

    @PostMapping("{productId}/edit")
    public String editProduct(Model model, @PathVariable("productId") int id,
                              UpdateProductPayload payload, @AuthenticationPrincipal OidcUser user) throws AccessDeniedException {
        try {
            this.productRestClient.updateProduct(id, payload.title(), payload.details(), user.getSubject(), payload.price());
            model.addAttribute("product", this.productRestClient.findProduct(id).get());
            return "redirect:/online-market/products/%d/edit".formatted(id);
        } catch (BadRequestException exception) {
            model.addAttribute("errors", exception.getErrors());
            model.addAttribute("product", this.productRestClient.findProduct(id).get());
            return "products/edit";
        }
    }

    @GetMapping("/create")
    public String createProduct() {
        return "products/newProduct";
    }

    @PostMapping("/create")
    public String createProduct(NewProductPayload payload,
                                Model model, @AuthenticationPrincipal OidcUser user) {
        try {
            Product product = this.productRestClient.createProduct(payload.title(), payload.details(),
                    user.getSubject(), payload.price());
            return "redirect:/online-market/products/%d/edit".formatted(product.getId());
        } catch (BadRequestException exception) {
            model.addAttribute("errors", exception.getErrors());
            return "products/newProduct";
        }
    }

    @PostMapping("{productId}/delete")
    public String deleteProduct(@PathVariable("productId") int id, Model model, @AuthenticationPrincipal OidcUser user) {
        try {
            this.productRestClient.deleteProduct(id);
            return "redirect:/online-market/products/list";
        } catch (NoSuchElementException exception) {
            model.addAttribute("errors", exception.getMessage());
            return "products/edit";
        }
    }
}
