package online_market.user_app.client.product;

import lombok.RequiredArgsConstructor;
import online_market.user_app.client.exception.BadRequestException;
import online_market.user_app.entity.Product;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;

@RequiredArgsConstructor
public class MainProductRestClient implements ProductRestClient {

    private static final ParameterizedTypeReference<List<Product>> PRODUCTS_TYPE_REFERENCE =

            new ParameterizedTypeReference<>() {
            };

    private final RestClient restClient;

    @Override
    public List<Product> getAllProduct(String filter) {
        return this.restClient.get()
                .uri("products-service-api/products?filter={filter}",filter)
                .retrieve()
                .body(PRODUCTS_TYPE_REFERENCE);
    }

    @Override
    public Product getProduct(int id) {
        try {
        return this.restClient.get()
                .uri("products-service-api/products/product/"+id)
                .retrieve()
                .body(Product.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>)problemDetail.getProperties().get("errors"));
        }
    }
}
