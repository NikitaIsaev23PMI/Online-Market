package online_market.products_service.services.product;

import lombok.RequiredArgsConstructor;
import online_market.products_service.entity.Product;
import online_market.products_service.repository.ProductMediaRepository;
import online_market.products_service.repository.ProductRepository;
import online_market.products_service.services.productMedia.ProductMediaStorageService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MainProductService implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMediaStorageService productMediaStorageService;

    private final ProductMediaRepository productMediaRepository;

    @Override
    public List<Product> findAllProduct(String filter) {
        return productRepository.findAllByTitleContainsIgnoreCase(filter);
    }

    @Override
    public Product findById(int id) {
        return this.productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("товар не найден"));
    }

    @Override
    public Product create(String title, String details, String sellerSubject, BigDecimal price) {
        return productRepository.save(new Product(null,title,details,sellerSubject,null, price, null));
    }

    @Override
    public void updateProduct(int id, String title, String details, String sellerSubject, BigDecimal price) {
        this.productRepository.findById(id).ifPresentOrElse(
                product -> {
                    if (product.getSellerSubject().equals(sellerSubject)) {
                        product.setTitle(title);
                        product.setDetails(details);
                        product.setPrice(price);
                        productRepository.save(product);
                    } else throw new AccessDeniedException("Вы не являетесь владельцем товара, поэтому не можете его редпктировать");
                }, () -> {
                    throw new NoSuchElementException("Товар не найден");
                }
        );
    }

    @Override
    public void deleteProduct(int id, String sellerSubject){
        if(this.productRepository.findById(id).isPresent()){
            if(this.productRepository.findById(id).get().getSellerSubject().equals(sellerSubject)){
                this.productRepository.deleteById(id);
            } else throw new AccessDeniedException("Вы не можете удалить этот товар");
        } else {
            throw new NoSuchElementException("Товар не найден");
        }
    }

    @Override
    public List<Product> findProductsBySellerSubject(String sellerSubject) {
        return this.productRepository.findAllBySellerSubject(sellerSubject);
    }

    @Override
    @Deprecated
    public Product findProductBySellerSubject(String sellerSubject) {
        if (this.productRepository.findProductBySellerSubject(sellerSubject).isPresent()){
            return this.productRepository.findProductBySellerSubject(sellerSubject).get();
        } else throw new NoSuchElementException("товар не найден");
    }

    @Override
    public List<Product> findProductsByListIds(List<Integer> listOfId) {
        return this.productRepository.findByIdIn(listOfId);
    }


}
