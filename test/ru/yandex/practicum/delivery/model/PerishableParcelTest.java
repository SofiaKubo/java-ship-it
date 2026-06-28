package ru.yandex.practicum.delivery.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PerishableParcelTest {
    private static final int SEND_DAY = 10;
    private static final int TIME_TO_LIVE = 3;
    private static final int EXPIRATION_DAY = SEND_DAY + TIME_TO_LIVE;

    private PerishableParcel perishableParcel;

    @BeforeEach
    void setUp() {
        perishableParcel = new PerishableParcel(
                "Йогурт",
                2,
                "Москва",
                SEND_DAY,
                TIME_TO_LIVE
        );
    }

    @Test
    void isExpiredShouldReturnFalseBeforeExpirationDate() {
        boolean actualResult = perishableParcel.isExpired(EXPIRATION_DAY - 1);
        assertThat(actualResult).isFalse();
    }

    @Test
    void isExpiredShouldReturnFalseOnExpirationDate() {
        boolean actualResult = perishableParcel.isExpired(EXPIRATION_DAY);
        assertThat(actualResult).isFalse();
    }

    @Test
    void isExpiredShouldReturnTrueAfterExpirationDate() {
        boolean actualResult = perishableParcel.isExpired(EXPIRATION_DAY + 1);
        assertThat(actualResult).isTrue();
    }
}
