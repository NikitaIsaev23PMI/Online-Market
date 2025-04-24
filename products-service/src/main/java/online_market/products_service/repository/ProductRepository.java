package online_market.products_service.repository;

import online_market.products_service.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByTitleContainsIgnoreCase(String filter);

    @EntityGraph(attributePaths = {"productMedia","seller"})
    Optional<Product> findById(int id);

    Product save(Product product);

    List<Product> findAllBySellerSubject(String sellerSubject);

    Optional<Product> findProductBySellerSubject(String sellerSubject);

    @EntityGraph(attributePaths = {"seller"})
    List<Product> findByIdIn(List<Integer> listOfId);

    //  List<Product> findAllByTitleContainsAndCategoryIs(String filter, String category);

    @Query("SELECT p FROM Product p WHERE " +
            "(LENGTH(:filter) = 0 OR p.title LIKE %:filter%) AND " +
            "(LENGTH(:category) = 0 OR p.category = :category)")
    List<Product> findAllByTitleContainsAndCategoryIs(
            @Param("filter") String filter,
            @Param("category") String category
    );


}
