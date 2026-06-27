package ru.yandex.practicum.delivery.model;

import java.util.ArrayList;
import java.util.List;

public class ParcelBox<T extends Parcel> {
    private final int maxWeight;
    private final List<T> parcels = new ArrayList<>();

    public ParcelBox(int maxWeight) {
        validateMaxWeight(maxWeight);
        this.maxWeight = maxWeight;
    }

    private int getCurrentWeight() {
        int currentWeight = 0;
        for (T parcel : parcels) {
            currentWeight += parcel.getWeight();
        }
        return currentWeight;
    }

    public boolean addParcel(T parcel) {
        validateParcel(parcel);

        if (!hasEnoughSpaceFor(parcel)) {
            return false;
        }
        parcels.add(parcel);
        return true;
    }

    public List<T> getAllParcels() {
        return List.copyOf(parcels);
    }

    private static void validateMaxWeight(int maxWeight) {
        if (maxWeight <= 0) {
            throw new IllegalArgumentException("Максимальный вес коробки должен быть положительным.");
        }
    }

    private static void validateParcel(Parcel parcel) {
        if (parcel == null) {
            throw new IllegalArgumentException("Посылка не может быть null.");
        }
    }

    private boolean hasEnoughSpaceFor(T parcel) {
        return getCurrentWeight() + parcel.getWeight() <= maxWeight;
    }
}
