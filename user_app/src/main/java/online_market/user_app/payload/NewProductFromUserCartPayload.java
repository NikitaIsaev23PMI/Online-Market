package online_market.user_app.payload;

public record NewProductFromUserCartPayload (
        Integer productId,

        String userName
){
}
