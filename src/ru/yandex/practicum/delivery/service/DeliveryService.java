package ru.yandex.practicum.delivery.service;

import java.util.ArrayList;
import java.util.List;

import ru.yandex.practicum.delivery.model.FragileParcel;
import ru.yandex.practicum.delivery.model.Parcel;
import ru.yandex.practicum.delivery.model.ParcelBox;
import ru.yandex.practicum.delivery.model.PerishableParcel;
import ru.yandex.practicum.delivery.model.StandardParcel;
import ru.yandex.practicum.delivery.model.Trackable;

public class DeliveryService {
    private static final int MAX_STANDARD_BOX_WEIGHT = 50;
    private static final int MAX_FRAGILE_BOX_WEIGHT = 30;
    private static final int MAX_PERISHABLE_BOX_WEIGHT = 20;
    private final List<Parcel> allParcels = new ArrayList<>();
    private final List<Trackable> trackableItems = new ArrayList<>();

    private final ParcelBox<StandardParcel> standardBox =
            new ParcelBox<>(MAX_STANDARD_BOX_WEIGHT);
    private final ParcelBox<FragileParcel> fragileBox =
            new ParcelBox<>(MAX_FRAGILE_BOX_WEIGHT);
    private final ParcelBox<PerishableParcel> perishableBox =
            new ParcelBox<>(MAX_PERISHABLE_BOX_WEIGHT);

    public boolean addParcel(Parcel parcel) {
        validateParcel(parcel);

        boolean isParcelAdded = switch (parcel) {
            case StandardParcel standardParcel -> standardBox.addParcel(standardParcel);
            case FragileParcel fragileParcel -> fragileBox.addParcel(fragileParcel);
            case PerishableParcel perishableParcel -> perishableBox.addParcel(perishableParcel);
            default -> throw new IllegalArgumentException(
                    "Неподдерживаемый тип посылки: " +
                            parcel.getClass().getSimpleName()
            );
        };

        if (isParcelAdded) {
            allParcels.add(parcel);
            if (parcel instanceof Trackable trackableParcel) {
                trackableItems.add(trackableParcel);
            }
        }
        return isParcelAdded;
    }

    public void updateTrackableItemsLocation(String newLocation) {
        validateNewLocation(newLocation);

        for (Trackable item : trackableItems) {
            item.reportStatus(newLocation);
        }
    }

    public void sendParcels() {
        for (Parcel parcel : allParcels) {
            parcel.packageItem();
            parcel.deliver();
        }
    }

    public int calculateTotalCost() {
        int totalCost = 0;
        for (Parcel parcel : allParcels) {
            totalCost += parcel.calculateDeliveryCost();
        }
        return totalCost;
    }

    private static void validateParcel(Parcel parcel) {
        if (parcel == null) {
            throw new IllegalArgumentException("Посылка не может быть null.");
        }
    }

    private static void validateNewLocation(String newLocation) {
        if (newLocation == null || newLocation.isBlank()) {
            throw new IllegalArgumentException(
                    "Новое местоположение не может быть пустым."
            );
        }
    }

    public List<StandardParcel> getStandardParcels() {
        return standardBox.getAllParcels();
    }

    public List<FragileParcel> getFragileParcels() {
        return fragileBox.getAllParcels();
    }

    public List<PerishableParcel> getPerishableParcels() {
        return perishableBox.getAllParcels();
    }
}
