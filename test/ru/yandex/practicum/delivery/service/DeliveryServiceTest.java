package ru.yandex.practicum.delivery.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.yandex.practicum.delivery.model.FragileParcel;
import ru.yandex.practicum.delivery.model.PerishableParcel;
import ru.yandex.practicum.delivery.model.StandardParcel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DeliveryServiceTest {
    private DeliveryService deliveryService;

    @BeforeEach
    void setUp() {
        deliveryService = new DeliveryService();
    }

    @Test
    void addParcelShouldAddStandardParcelToStandardBox() {
        StandardParcel standardParcel = new StandardParcel("Документы", 5, "Москва", 3);

        boolean result = deliveryService.addParcel(standardParcel);

        assertThat(result).isTrue();
        assertThat(deliveryService.getStandardParcels()).hasSize(1);
        assertThat(deliveryService.getStandardParcels()).contains(standardParcel);
    }

    @Test
    void addParcelShouldAddFragileParcelToFragileBox() {
        FragileParcel fragileParcel = new FragileParcel("Ваза", 2, "Москва", 3);

        boolean result = deliveryService.addParcel(fragileParcel);

        assertThat(result).isTrue();
        assertThat(deliveryService.getFragileParcels()).hasSize(1);
        assertThat(deliveryService.getFragileParcels()).contains(fragileParcel);
    }

    @Test
    void addParcelShouldAddPerishableParcelToPerishableBox() {
        PerishableParcel perishableParcel = new PerishableParcel(
                "Йогурт",
                3,
                "Москва",
                3,
                2
        );

        boolean result = deliveryService.addParcel(perishableParcel);

        assertThat(result).isTrue();
        assertThat(deliveryService.getPerishableParcels()).hasSize(1);
        assertThat(deliveryService.getPerishableParcels()).contains(perishableParcel);
    }

    @Test
    void addParcelShouldReturnFalseWhenStandardBoxIsOverweight() {
        StandardParcel standardParcel = new StandardParcel("Документы", 51, "Москва", 3);

        boolean result = deliveryService.addParcel(standardParcel);

        int actualTotalCost = deliveryService.calculateTotalCost();

        assertThat(result).isFalse();
        assertThat(deliveryService.getStandardParcels()).isEmpty();
        assertThat(actualTotalCost).isEqualTo(0);
    }

    @Test
    void calculateTotalCostShouldReturnSumForAllAddedParcels() {
        StandardParcel standardParcel = new StandardParcel("Документы", 5, "Москва", 3);
        FragileParcel fragileParcel = new FragileParcel("Ваза", 3, "Москва", 3);
        PerishableParcel perishableParcel = new PerishableParcel(
                "Йогурт",
                4,
                "Москва",
                3,
                2
        );

        deliveryService.addParcel(standardParcel);
        deliveryService.addParcel(fragileParcel);
        deliveryService.addParcel(perishableParcel);

        int actualTotalCost = deliveryService.calculateTotalCost();

        assertThat(actualTotalCost).isEqualTo(34);
    }

    @Test
    void addParcelShouldRejectNullParcel() {
        assertThatThrownBy(() -> deliveryService.addParcel(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Посылка не может быть null.");
    }

    @Test
    void updateTrackableItemsLocationShouldRejectBlankLocation() {
        assertThatThrownBy(() -> deliveryService.updateTrackableItemsLocation("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Новое местоположение не может быть пустым.");
    }
}

