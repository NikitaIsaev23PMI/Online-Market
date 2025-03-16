import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("products-service-api/products/")
@RequiredArgsConstructor
public class ProductsRestController {

    private final ProductsService productsService;

}
