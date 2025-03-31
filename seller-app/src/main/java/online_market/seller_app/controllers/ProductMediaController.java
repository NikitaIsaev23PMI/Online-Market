package online_market.seller_app.controllers;

import lombok.RequiredArgsConstructor;
import online_market.seller_app.client.exception.BadRequestException;
import online_market.seller_app.client.ProductMediaRestClient;
import online_market.seller_app.client.exception.MediaUploadException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("online-market/products-media")
@RequiredArgsConstructor
public class ProductMediaController {

    private final ProductMediaRestClient productMediaRestClient;

    @PostMapping("/upload/media/{productId}")
    public String uploadMedia(@RequestParam("media") MultipartFile media,
                              @PathVariable("productId") Integer productId,
                              RedirectAttributes redirectAttributes) {
        try {
            this.productMediaRestClient.addMedia(media, productId);
            return "redirect:/online-market/products/%d/edit".formatted(productId);
        } catch (MediaUploadException exception){
            redirectAttributes.addFlashAttribute("errors", exception.getMessage());
            return "redirect:/online-market/products/%d/edit".formatted(productId);
        }
    }

    @PostMapping("/delete/media/{productId}/{mediaId}")
    public String deleteMedia(@PathVariable("mediaId") Integer mediaId,
                              @PathVariable("productId") Integer productId,
                              RedirectAttributes redirectAttributes) {
        try {
            this.productMediaRestClient.deleteProductMedia(mediaId);
            return "redirect:/online-market/products/%d/edit".formatted(productId);
        } catch (HttpClientErrorException.NotFound exception){
            redirectAttributes.addAttribute("errors", exception.getResponseBodyAsString());
            return "redirect:/online-market/products/%d/edit".formatted(productId);
        } catch (BadRequestException exception){
            redirectAttributes.addAttribute("errors", exception.getMessage());
            return "redirect:/online-market/products/%d/edit".formatted(productId);
        }
    }
}
