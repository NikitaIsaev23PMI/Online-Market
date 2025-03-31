package online_market.seller_app.client;

import lombok.RequiredArgsConstructor;
import online_market.seller_app.client.exception.BadRequestException;
import online_market.seller_app.client.exception.MediaUploadException;
import online_market.seller_app.entity.ProductMedia;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
public class MainProductMediaRestClient implements ProductMediaRestClient {

    private static final ParameterizedTypeReference<List<ProductMedia>> PRODUCTS_MEDIA_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {
            };

    private final RestClient restClient;

    @Override
    public ProductMedia addMedia(MultipartFile file, Integer productId){
        try {
            MultiValueMap<String, Object> media = new LinkedMultiValueMap<>();
            media.add("media", file.getResource());
            return this.restClient.post()
                    .uri("products-service-api/products-media/upload-media/{productId}", productId)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(media)
                    .retrieve()
                    .body(ProductMedia.class);
        } catch (HttpClientErrorException.BadRequest exception){
            System.out.println(exception.getResponseBodyAsString());
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            String errorMessage = (String)problemDetail.getProperties().get("errors");
            throw new MediaUploadException(errorMessage);
        } catch (HttpClientErrorException.NotFound exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (HttpServerErrorException.InternalServerError exception){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<ProductMedia> getProductMedia(int productId) {
        return this.restClient.get()
                .uri("products-service-api/products-media/download-media/{productId}", productId)
                .retrieve()
                .body(PRODUCTS_MEDIA_TYPE_REFERENCE);
    }

    @Override
    public void deleteProductMedia(Integer mediaId) {
        try {
            this.restClient.delete()
                    .uri("products-service-api/products-media/delete-media/{mediaId}", mediaId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
