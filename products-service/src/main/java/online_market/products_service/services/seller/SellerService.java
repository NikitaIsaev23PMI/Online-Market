package online_market.products_service.services.seller;

import online_market.products_service.entity.Seller;

public interface SellerService {

    Seller CreateNewOrReturnExistSeller(String sellerSubject,
                                        String email,
                                        String preferredUsername);
}
