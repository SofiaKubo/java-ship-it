package ru.yandex.practicum.delivery.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ParcelValidationTest {
    @Test
    void parcelShouldRejectBlankDescription() {
        assertThatThrownBy(() -> new StandardParcel("   ", 5, "Москва", 3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Описание посылки не может быть пустым.");
    }

    @Test
    void parcelShouldRejectNullDescription() {
        assertThatThrownBy(() -> new StandardParcel(null, 5, "Москва", 3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Описание посылки не может быть пустым.");
    }

    @Test
    void parcelShouldRejectZeroWeight() {
        assertThatThrownBy(() -> new StandardParcel("Документы", 0, "Москва", 3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Вес посылки должен быть положительным.");
    }

    @Test
    void parcelShouldRejectNegativeWeight() {
        assertThatThrownBy(() -> new StandardParcel("Документы", -5, "Москва", 3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Вес посылки должен быть положительным.");
    }

    @Test
    void parcelShouldRejectBlankDeliveryAddress() {
        assertThatThrownBy(() -> new StandardParcel("Документы", 5, "  ", 3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Адрес доставки не может быть пустым.");
    }

    @Test
    void parcelShouldRejectNullDeliveryAddress() {
        assertThatThrownBy(() -> new StandardParcel("Документы", 5, null, 3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Адрес доставки не может быть пустым.");
    }

    @Test
    void parcelShouldRejectZeroSendDay() {
        assertThatThrownBy(() -> new StandardParcel("Документы", 5, "Москва", 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("День отправки должен быть положительным числом.");
    }

    @Test
    void parcelShouldRejectNegativeSendDay() {
        assertThatThrownBy(() -> new StandardParcel("Документы", 5, "Москва", -3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("День отправки должен быть положительным числом.");
    }

    @Test
    void perishableParcelShouldRejectZeroTimeToLive() {
        assertThatThrownBy(() -> new PerishableParcel("Йогурт", 2, "Москва", 14, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Срок хранения посылки должен быть положительным числом.");
    }

    @Test
    void perishableParcelShouldRejectNegativeTimeToLive() {
        assertThatThrownBy(() -> new PerishableParcel("Йогурт", 2, "Москва", 14, -3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Срок хранения посылки должен быть положительным числом.");
    }

    @Test
    void parcelBoxShouldRejectZeroMaxWeight() {
        assertThatThrownBy(() -> new ParcelBox<StandardParcel>(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Максимальный вес коробки должен быть положительным.");
    }

    @Test
    void parcelBoxShouldRejectNegativeMaxWeight() {
        assertThatThrownBy(() -> new ParcelBox<StandardParcel>(-10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Максимальный вес коробки должен быть положительным.");
    }

    @Test
    void addParcelShouldRejectNullParcel() {
        ParcelBox<StandardParcel> parcelBox = new ParcelBox<>(10);

        assertThatThrownBy(() -> parcelBox.addParcel(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Посылка не может быть null.");
    }
}
