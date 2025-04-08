package online_market.products_service.controllers;

import lombok.RequiredArgsConstructor;
import online_market.products_service.entity.ProductMedia;
import online_market.products_service.services.productMedia.ProductMediaStorageService;
import org.apache.coyote.BadRequestException;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("products-service-api/products-media")
@RequiredArgsConstructor
public class ProductsMediaRestController {

    private final ProductMediaStorageService productMediaStorageService;

    @PostMapping("/upload-media/{productId}")
    public ResponseEntity<?> uploadMedia(@RequestParam("media") MultipartFile media,
                                         @PathVariable("productId") Integer productId) throws IOException {
        try {
            ProductMedia productMedia = this.productMediaStorageService.addProductMedia(media, productId);
            return ResponseEntity.ok().body(productMedia);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (BadRequestException exception) {
            ProblemDetail problemDetail = ProblemDetail
                    .forStatusAndDetail(HttpStatus.BAD_REQUEST, "у товара может быть не более 6 фото или видео");
            problemDetail.setProperty("errors", exception.getMessage());
            return ResponseEntity.badRequest().body(problemDetail);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/download-media/{productId}")
    public ResponseEntity<List<ProductMedia>> downloadMedia(@PathVariable("productId") Integer productId){
        try {
            return ResponseEntity.ok().body(this.productMediaStorageService.findProductMediaByProductId(productId));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/delete-media/{mediaId}")
    public ResponseEntity<?> deleteMedia(@PathVariable("mediaId") Integer mediaId) throws IOException {
        try {
            this.productMediaStorageService.deleteProductMedia(mediaId);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("{name-media}")
    public ResponseEntity<?> findMediaByName(@PathVariable("name-media") String mediaName) throws IOException {
        Resource media = this.productMediaStorageService.getMedia(mediaName);
        String contentType = Files.probeContentType(Paths.get(media.getFile().getAbsolutePath()));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + mediaName + "\"")
                .body(media);
    }
}
