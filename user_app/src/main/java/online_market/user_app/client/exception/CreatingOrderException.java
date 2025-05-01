package online_market.user_app.client.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=true)
public class CreatingOrderException extends RuntimeException {

    private final List<String> errors;

    public CreatingOrderException(List<String> errors) {
        this.errors = errors;
    }
}
