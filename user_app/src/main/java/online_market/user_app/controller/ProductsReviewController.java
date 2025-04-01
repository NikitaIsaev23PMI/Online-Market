package online_market.user_app.controller;

import lombok.RequiredArgsConstructor;
import online_market.user_app.client.exception.BadRequestException;
import online_market.user_app.client.productReview.ProductReviewRestClient;
import online_market.user_app.entity.ProductReview;
import online_market.user_app.payload.NewProductReviewPayload;
import online_market.user_app.payload.UpdateProductReviewPayload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("online-market/buyer/products-review")
@RequiredArgsConstructor
public class ProductsReviewController {

    private final ProductReviewRestClient productReviewRestClient;

    @PostMapping("/upload-review")
    public String uploadReview(NewProductReviewPayload payload,
                               RedirectAttributes redirectAttributes) {
        try {
            System.out.println(payload.toString());
            this.productReviewRestClient.addProductReview(payload.username(), payload.productId(),
                    payload.review(), payload.rating());
            return "redirect:/online-market/buyer/products/%d".formatted(payload.productId());
        } catch (BadRequestException exception){
            redirectAttributes.addFlashAttribute("errors", exception.getErrors());;
            return "redirect:/online-market/buyer/products/%d".formatted(payload.productId());
        }
    }

    @PostMapping("/delete-review/{productId}")
    public String deleteReview(@PathVariable("productId") Integer productId,
                               @AuthenticationPrincipal OidcUser principal,
                               RedirectAttributes redirectAttributes) {
        try {
            this.productReviewRestClient.deleteProductReview(productId, principal.getPreferredUsername());
            return "redirect:/online-market/buyer/products/%d".formatted(productId);
        } catch (NoSuchElementException exception){
            redirectAttributes.addFlashAttribute("errors", exception.getMessage());
            return "redirect:/online-market/buyer/products/%d".formatted(productId);
        }
    }

    @PostMapping("/edit-review")
    public String editReview(UpdateProductReviewPayload payload,
                             RedirectAttributes redirectAttributes) {
        try {
            this.productReviewRestClient.editProductReview(payload.username(), payload.productId(),
                    payload.review(), payload.rating());
            return "redirect:/online-market/buyer/products/%d".formatted(payload.productId());
        } catch (NoSuchElementException exception){
            redirectAttributes.addFlashAttribute("errors", exception.getMessage());
            return "redirect:/online-market/buyer/products/%d".formatted(payload.productId());
        }
    }
}
