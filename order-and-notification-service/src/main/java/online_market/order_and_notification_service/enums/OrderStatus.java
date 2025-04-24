package online_market.order_and_notification_service.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {

    PROCESSING,

    ASSEMBLY,

    ON_THE_WAY,

    DELIVERED,

    COMPLETED;

    public static OrderStatus fromString(String text) {
        for (OrderStatus order : OrderStatus.values()) {
            if (text.equalsIgnoreCase(order.toString())) {
                return order;
            }
        }
        throw new IllegalArgumentException("Неправильно указан статус " + text);
    }
}
