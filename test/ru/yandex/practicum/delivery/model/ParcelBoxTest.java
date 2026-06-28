package ru.yandex.practicum.delivery.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ParcelBoxTest {
    @Test
    void addParcelShouldReturnTrueWhenParcelFitsIntoEmptyBox() {
        ParcelBox<StandardParcel> parcelBox = new ParcelBox<>(10);

        StandardParcel parcel = new StandardParcel("Документы", 5, "Москва", 3);

        boolean result = parcelBox.addParcel(parcel);

        assertThat(result).isTrue();
        assertThat(parcelBox.getAllParcels()).hasSize(1);
        assertThat(parcelBox.getAllParcels()).contains(parcel);
    }

    @Test
    void addParcelShouldReturnFalseWhenParcelIsHeavierThanMaxWeight() {
        ParcelBox<StandardParcel> parcelBox = new ParcelBox<>(10);

        StandardParcel parcel = new StandardParcel("Документы", 11, "Москва", 3);

        boolean result = parcelBox.addParcel(parcel);

        assertThat(result).isFalse();
        assertThat(parcelBox.getAllParcels()).isEmpty();
    }

    @Test
    void addParcelShouldReturnTrueWhenTotalWeightEqualsMaxWeight() {
        ParcelBox<StandardParcel> parcelBox = new ParcelBox<>(10);

        StandardParcel firstParcel = new StandardParcel("Документы", 6, "Москва", 3);
        StandardParcel secondParcel = new StandardParcel("Фотографии", 4, "Москва", 3);

        boolean firstResult = parcelBox.addParcel(firstParcel);
        boolean secondResult = parcelBox.addParcel(secondParcel);

        assertThat(firstResult).isTrue();
        assertThat(secondResult).isTrue();
        assertThat(parcelBox.getAllParcels()).hasSize(2);
        assertThat(parcelBox.getAllParcels()).contains(firstParcel, secondParcel);
    }

    @Test
    void addParcelShouldReturnFalseWhenTotalWeightExceedsMaxWeight() {
        ParcelBox<StandardParcel> parcelBox = new ParcelBox<>(10);

        StandardParcel firstParcel = new StandardParcel("Документы", 6, "Москва", 3);
        StandardParcel secondParcel = new StandardParcel("Фотографии", 7, "Москва", 3);

        boolean firstResult = parcelBox.addParcel(firstParcel);
        boolean secondResult = parcelBox.addParcel(secondParcel);

        assertThat(firstResult).isTrue();
        assertThat(secondResult).isFalse();
        assertThat(parcelBox.getAllParcels()).hasSize(1);
        assertThat(parcelBox.getAllParcels()).contains(firstParcel);
        assertThat(parcelBox.getAllParcels()).doesNotContain(secondParcel);
    }
}
