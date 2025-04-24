package online_market.order_and_notification_service.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import online_market.order_and_notification_service.enums.OrderStatus;

@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderStatus, String> {

    @Override
    public String convertToDatabaseColumn(OrderStatus orderStatus) {
        return orderStatus == null ? null : orderStatus.name();
    }

    @Override
    public OrderStatus convertToEntityAttribute(String s) {
        return s == null ? null : OrderStatus.fromString(s);
    }
}
