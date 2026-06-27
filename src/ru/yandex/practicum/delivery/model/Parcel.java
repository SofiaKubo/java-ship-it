package ru.yandex.practicum.delivery.model;

public abstract class Parcel {
    private final String description;
    private final int weight;
    private final String deliveryAddress;
    private final int sendDay;

    protected Parcel(
            String description,
            int weight,
            String deliveryAddress,
            int sendDay
    ) {
        validateDescription(description);
        validateWeight(weight);
        validateDeliveryAddress(deliveryAddress);
        validateSendDay(sendDay);

        this.description = description.trim();
        this.weight = weight;
        this.deliveryAddress = deliveryAddress.trim();
        this.sendDay = sendDay;
    }

    public String getDescription() {
        return description;
    }

    public int getWeight() {
        return weight;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public int getSendDay() {
        return sendDay;
    }

    protected abstract int getPricePerKg();

    public void packageItem() {
        System.out.printf("Посылка %s упакована%n", getDescription());
    }

    public void deliver() {
        System.out.printf(
                "Посылка %s доставлена по адресу %s%n",
                getDescription(),
                getDeliveryAddress()
        );
    }

    public int calculateDeliveryCost() {
        return getWeight() * getPricePerKg();
    }

    private static void validateDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Описание посылки не может быть пустым.");
        }
    }

    private static void validateWeight(int weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Вес посылки должен быть положительным.");
        }
    }

    private static void validateDeliveryAddress(String deliveryAddress) {
        if (deliveryAddress == null || deliveryAddress.isBlank()) {
            throw new IllegalArgumentException("Адрес доставки не может быть пустым.");
        }
    }

    private static void validateSendDay(int sendDay) {
        if (sendDay <= 0) {
            throw new IllegalArgumentException(
                    "День отправки должен быть положительным числом."
            );
        }
    }
}
