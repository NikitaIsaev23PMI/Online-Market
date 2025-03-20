package online_market.app.comments_and_shopping_cart_service.service;

import lombok.RequiredArgsConstructor;
import online_market.app.comments_and_shopping_cart_service.entity.ProductFromCart;
import online_market.app.comments_and_shopping_cart_service.repository.ProductsFromCartRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MainProductFromCartService implements ProductFromCartService{

    private final ProductsFromCartRepository productsFromCartRepository;

    @Override
    public ProductFromCart addProductToCart(Integer productId, String userName) {
        return this.productsFromCartRepository.save(new ProductFromCart(null, userName, productId));
    }

    @Override
    public List<ProductFromCart> findAllProductsFromUserCart(String userName) {
        return this.productsFromCartRepository.findAllByUserName(userName);
    }

    @Override
    public void deleteProductFromCart(Integer productId, String userName) {
        if(this.productsFromCartRepository.findProductFromCartByUserNameAndProductId(userName,productId).isPresent()){
            this.productsFromCartRepository.deleteByUserNameAndProductId(userName, productId);
        } else throw new IllegalArgumentException("Product not found");
    }

    @Override
    public Optional<ProductFromCart> findProductFromCartByUserNameAndProductId(String userName, Integer productId) {
        if (this.productsFromCartRepository.findProductFromCartByUserNameAndProductId(userName, productId).isPresent()){
            return this.productsFromCartRepository.findProductFromCartByUserNameAndProductId(userName, productId);
        } else throw new IllegalArgumentException("Product not found");
    }

}
