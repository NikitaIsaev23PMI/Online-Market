package online_market.seller_app.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {

    PROCESSING("В обработке"),

    ASSEMBLY("Сборка"),

    ON_THE_WAY("В пути"),

    DELIVERED("Доставлен"),

    COMPLETED("Завершён");

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    public static OrderStatus fromString(String text) {
        for (OrderStatus order : OrderStatus.values()) {
            if (text.equalsIgnoreCase(order.toString())) {
                return order;
            }
        }
        throw new IllegalArgumentException("Неправильно указан статус " + text);
    }
}
