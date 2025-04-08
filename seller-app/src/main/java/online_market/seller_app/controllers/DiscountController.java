package online_market.seller_app.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import online_market.seller_app.client.MainDiscountRestClient;
import online_market.seller_app.client.exception.BadRequestException;
import online_market.seller_app.entity.Product;
import online_market.seller_app.payload.NewDiscountPayload;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("online-market/products/discount")
@RequiredArgsConstructor
public class DiscountController {

    private final MainDiscountRestClient mainDiscountRestClient;

    @PostMapping("{productId}")
    public String addDiscount(@PathVariable("productId") Integer productId,
                              NewDiscountPayload payload, RedirectAttributes redirectAttributes,
                              HttpServletRequest request) {
        try {
            this.mainDiscountRestClient.SetDiscountForProduct(productId, payload.amount(), payload.endDiscount());
            return "redirect:" + request.getHeader("Referer");
        } catch (BadRequestException exception) {
            redirectAttributes.addFlashAttribute("errors", exception.getErrors());
            return "redirect:" + request.getHeader("Referer");
        }
    }

    @PostMapping("delete/{prdouctId}")
    public String deleteDiscount(@PathVariable("productId") Integer productId,
                                 RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
            this.mainDiscountRestClient.deleteDiscount(productId);
            return "redirect:" + request.getHeader("Referer");
        } catch (NoSuchElementException exception) {
            redirectAttributes.addFlashAttribute("errors", exception.getMessage());
            return "redirect:" + request.getHeader("Referer");
        }
    }
}
