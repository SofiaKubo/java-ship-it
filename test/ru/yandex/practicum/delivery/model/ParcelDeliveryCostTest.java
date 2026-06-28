package ru.yandex.practicum.delivery.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ParcelDeliveryCostTest {
    @Test
    void standardParcelShouldCalculateDeliveryCost() {
        StandardParcel parcel = new StandardParcel("Документы", 5, "Москва", 3);

        int actualCost = parcel.calculateDeliveryCost();

        assertThat(actualCost).isEqualTo(10);
    }

    @Test
    void fragileParcelShouldCalculateDeliveryCost() {
        FragileParcel fragileParcel = new FragileParcel("Ваза", 3, "Москва", 13);

        int actualCost = fragileParcel.calculateDeliveryCost();

        assertThat(actualCost).isEqualTo(12);
    }

    @Test
    void perishableParcelShouldCalculateDeliveryCost() {
        PerishableParcel perishableParcel = new PerishableParcel(
                "Паштет",
                5,
                "Москва",
                23,
                2
        );

        int actualCost = perishableParcel.calculateDeliveryCost();

        assertThat(actualCost).isEqualTo(15);
    }
}
