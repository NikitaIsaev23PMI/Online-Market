package online_market.user_app.client.productFromCart;

import lombok.RequiredArgsConstructor;
import online_market.user_app.client.exception.BadRequestException;
import online_market.user_app.entity.Product;
import online_market.user_app.entity.ProductFromCart;
import online_market.user_app.payload.NewProductFromUserCartPayload;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class MainProductFromUserCartClient implements ProductFromUserCartClient{

    private final RestClient restClient;

    private static final ParameterizedTypeReference<List<ProductFromCart>> PRODUCTS_TYPE_REFERENCE =

            new ParameterizedTypeReference<>() {
            };


    @Override
    public List<ProductFromCart> getAllProductsFromUserCart(String username) {
        return this.restClient.get()
                .uri("api/products-from-cart/{username}",username)
                .retrieve()
                .body(PRODUCTS_TYPE_REFERENCE);
    }

    @Override
    public ProductFromCart addProductFromUserCart(String username, Integer productId) {
        try {
            return this.restClient.post()
                    .uri("api/products-from-cart")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewProductFromUserCartPayload(productId, username))
                    .retrieve()
                    .body(ProductFromCart.class);
        } catch (HttpClientErrorException.BadRequest exception){
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>)problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void deleteProductFromUserCart(String username, Integer productId) {
        try {
            this.restClient.delete()
                    .uri("api/products-from-cart/products/{productId}/user/{username}",productId,username)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception){
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new NoSuchElementException((String)problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public ProductFromCart getProductFromUserCart(String username, Integer productId) {
        try {
            return this.restClient.get()
                    .uri("api/products-from-cart/products/{productId}/user/{username}",productId,username)
                    .retrieve()
                    .body(ProductFromCart.class);
        } catch (HttpClientErrorException.NotFound exception){
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new NoSuchElementException((String)problemDetail.getProperties().get("errors"));
        }
    }


}
