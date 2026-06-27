package ru.yandex.practicum.delivery.model;

public class StandardParcel extends Parcel {
    private static final int PRICE_PER_KG = 2;

    public StandardParcel(
            String description,
            int weight,
            String deliveryAddress,
            int sendDay
    ) {
        super(description, weight, deliveryAddress, sendDay);
    }

    @Override
    protected int getPricePerKg() {
        return PRICE_PER_KG;
    }
}
