package online_market.products_service.services.discount;

import lombok.RequiredArgsConstructor;
import online_market.products_service.entity.Discount;
import online_market.products_service.entity.Product;
import online_market.products_service.repository.DiscountRepository;
import online_market.products_service.repository.ProductRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.net.BindException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MainDiscountService implements DiscountService{

    private final DiscountRepository discountRepository;

    private final ProductRepository productRepository;

    @Override
    public void addDiscount(Integer productId, Integer amount, LocalDateTime endDate){
        productRepository.findById(productId)
                .ifPresentOrElse(product -> {
                    if(product.getDiscount() == null ) {
                        this.discountRepository.save(new Discount(null, product, amount, LocalDateTime.now(), endDate));
                    } else {
                        throw new IllegalStateException("Товар уже имеет активную скидку");
                    }
                }, () -> {throw new NoSuchElementException("Продукт не найден");}
                        );
    }

    @Override
    public void deleteDiscount(Integer productId) {
        this.productRepository.findById(productId).ifPresentOrElse(product -> {
            this.discountRepository.deleteDiscountByProductId(productId);
        }, () -> {throw new NoSuchElementException("Продукт не найден");});
    }
}
