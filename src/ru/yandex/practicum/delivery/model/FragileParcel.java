package ru.yandex.practicum.delivery.model;

public class FragileParcel extends Parcel implements Trackable {
    private static final int PRICE_PER_KG = 4;

    public FragileParcel(
            String description,
            int weight,
            String deliveryAddress,
            int sendDay
    ) {
        super(description, weight, deliveryAddress, sendDay);
    }

    @Override
    public void packageItem() {
        System.out.printf(
                "Посылка %s обёрнута в защитную плёнку%n",
                getDescription()
        );
        super.packageItem();
    }

    @Override
    public void reportStatus(String newLocation) {
        System.out.printf(
                "Хрупкая посылка %s изменила местоположение на %s%n",
                getDescription(),
                newLocation
        );
    }

    @Override
    protected int getPricePerKg() {
        return PRICE_PER_KG;
    }
}
