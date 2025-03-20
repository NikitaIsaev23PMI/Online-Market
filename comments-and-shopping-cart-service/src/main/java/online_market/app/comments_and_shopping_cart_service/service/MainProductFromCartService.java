package online_market.app.comments_and_shopping_cart_service.service;

import lombok.RequiredArgsConstructor;
import online_market.app.comments_and_shopping_cart_service.entity.ProductFromCart;
import online_market.app.comments_and_shopping_cart_service.repository.ProductsFromCartRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MainProductFromCartService implements ProductFromCartService{

    private final ProductsFromCartRepository productsFromCartRepository;

    @Override
    public List<ProductFromCart> findAllProductsFromUserCart(String userName) {
        return this.productsFromCartRepository.findAllByUserName(userName);
    }


    @Override
    public ProductFromCart addProductToCart(Integer productId, String userName) {
        if(this.productsFromCartRepository.findProductFromCartByUserNameAndProductId(userName, productId).isEmpty()){
            return this.productsFromCartRepository.save(new ProductFromCart(null, userName, productId));
        } else throw new IllegalStateException("Товар уже добавлен в вашу корзину");
    }


    @Override
    public void deleteProductFromCart(Integer productId, String userName) {
        if(this.productsFromCartRepository.findProductFromCartByUserNameAndProductId(userName,productId).isPresent()){
            this.productsFromCartRepository.deleteByUserNameAndProductId(userName, productId);
        } else throw new IllegalArgumentException("Товар не найден");
    }

    @Override
    public ProductFromCart findProductFromCartByUserNameAndProductId(String userName, Integer productId) {
        if (this.productsFromCartRepository.findProductFromCartByUserNameAndProductId(userName, productId).isPresent()){
            return this.productsFromCartRepository.findProductFromCartByUserNameAndProductId(userName, productId).get();
        } else throw new IllegalArgumentException("Товар не найден");
    }

    @Override
    @Deprecated
    public ProductFromCart findProductFromCartById(String id) {
        if(this.productsFromCartRepository.findById(id).isPresent()){
            return this.productsFromCartRepository.findById(id).get();
        } else throw new NoSuchElementException("Товар не найден");
    }

}
