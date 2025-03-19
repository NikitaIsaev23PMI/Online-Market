package online_market.seller_app.client;

import lombok.RequiredArgsConstructor;
import online_market.seller_app.entity.Product;
import online_market.seller_app.payload.NewProductPayload;
import online_market.seller_app.payload.UpdateProductPayload;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class MainProductsRestClient implements ProductRestClient{

    private static final ParameterizedTypeReference<List<Product>> PRODUCTS_TYPE_REFERENCE =

            new ParameterizedTypeReference<>() {
            };

    private final RestClient restClient;


    @Override
    public List<Product> findAllProducts(String filter) {
        return this.restClient.get()
                .uri("/products-service-api/products?filter={filter}",filter)
                .retrieve()
                .body(PRODUCTS_TYPE_REFERENCE);
    }

    @Override
    public Product createProduct(String title, String details, String sellerSubject) {
        try {
            return this.restClient.post()
                    .uri("/products-service-api/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewProductPayload(title, details, sellerSubject))
                    .retrieve()
                    .body(Product.class);
        } catch (HttpClientErrorException.BadRequest exception){
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>)problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void updateProduct(int id, String title, String details, String sellerSubject) {
        try {
            this.restClient.patch()
                    .uri("products-service-api/products/" + id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UpdateProductPayload(title, details, sellerSubject))
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception){
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>)problemDetail.getProperties().get("errors"));
        } catch (HttpClientErrorException.Forbidden exception){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public Optional<Product> findProduct(int id) {
        try {
            return Optional.ofNullable(this.restClient.get()
                    .uri("/products-service-api/products/product/" + id)
                    .retrieve()
                    .body(Product.class));
        }catch (HttpClientErrorException.NotFound exception){
            return Optional.empty();
        }
    }


    @Override
    public void deleteProduct(int id) {
        try {
            this.restClient.delete()
                    .uri("/products-service-api/products/" + id)
                    .retrieve()
                    .toBodilessEntity();
        }catch (HttpClientErrorException.NotFound exception){
            throw new NoSuchElementException("Нельзя удалить товар с данным Id, так как его изначально не существовало");
        }
    }

    @Override
    public List<Product> getSellerProducts(String sellerSubject) {
        return this.restClient.get()
                .uri("/products-service-api/products/{sub}", sellerSubject)
                .retrieve()
                .body(PRODUCTS_TYPE_REFERENCE);
    }
}

