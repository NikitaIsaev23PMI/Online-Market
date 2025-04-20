package online_market.products_service.services.seller;

import lombok.RequiredArgsConstructor;
import online_market.products_service.entity.Seller;
import online_market.products_service.repository.SellerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MainSellerService implements SellerService {

    private final SellerRepository sellerRepository;

    @Override
    public Seller CreateNewOrReturnExistSeller(String sellerSubject, String email, String preferredUsername) {
        Optional<Seller> seller = this.sellerRepository.findSellerBySubject(sellerSubject);
        return seller.orElseGet(() -> this.sellerRepository
                .save(new Seller(null, preferredUsername, email, sellerSubject, null)));
    }
}
