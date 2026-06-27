package ru.yandex.practicum.delivery.model;

public class PerishableParcel extends Parcel {
    private static final int PRICE_PER_KG = 3;
    private final int timeToLive;

    public PerishableParcel(
            String description,
            int weight,
            String deliveryAddress,
            int sendDay,
            int timeToLive
    ) {
        super(description, weight, deliveryAddress, sendDay);
        validateTimeToLive(timeToLive);

        this.timeToLive = timeToLive;
    }

    public boolean isExpired(int currentDay) {
        return (getSendDay() + timeToLive) < currentDay;
    }

    @Override
    protected int getPricePerKg() {
        return PRICE_PER_KG;
    }

    private static void validateTimeToLive(int timeToLive) {
        if (timeToLive <= 0) {
            throw new IllegalArgumentException(
                    "Срок хранения посылки должен быть положительным числом."
            );
        }
    }
}
