package online_market.user_app.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seller {

    private Integer id;

    private String preferredUsername;

    private String email;

    private String subject;

    private List<Product> products;
}
