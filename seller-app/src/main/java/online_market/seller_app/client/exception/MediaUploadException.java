package online_market.seller_app.client.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class MediaUploadException extends RuntimeException {

    public MediaUploadException(String message) {
        super(message);
    }
}
