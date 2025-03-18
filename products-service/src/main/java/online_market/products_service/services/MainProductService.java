package online_market.products_service.services;

import lombok.RequiredArgsConstructor;
import online_market.products_service.entity.Product;
import online_market.products_service.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MainProductService implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> findAllProduct(String filter) {
        return productRepository.findAllByTitleContainsIgnoreCase(filter);
    }

    @Override
    public Product findById(int id) {
        return this.productRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Product create(String title, String details, String sellerUserName) {
        return productRepository.save(new Product(null,title,details,sellerUserName));
    }

    @Override
    public void updateProduct(int id, String title, String details) {
        this.productRepository.findById(id).ifPresentOrElse(
                product -> {
                    product.setTitle(title);
                    product.setDetails(details);
                    productRepository.save(product);
                }, () -> {
                    throw new NoSuchElementException();
                }
        );
    }

    @Override
    public void deleteProduct(int id) {
        if(this.productRepository.existsById(id)){
            this.productRepository.deleteById(id);
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public List<Product> findProductsBySellerUserName(String sellerUserName) {
        return this.productRepository.findAllBySellerUserName(sellerUserName);
    }
}
