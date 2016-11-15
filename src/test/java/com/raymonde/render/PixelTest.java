package com.raymonde.render;

import lombok.val;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PixelTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfBothArgumentsAreInvalid() {
        new Pixel(-5, -5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfFirstArgumentIsNegative() {
        new Pixel(-5, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfSecondArgumentIsNegative() {
        new Pixel(1, -1);
    }

    @Test
    public void gettersShouldReturnTheRightValuesAfterConstruction() {
        // Given
        val p = new Pixel(1, 6);

        // Expect
        assertThat(p.x()).isEqualTo(1);
        assertThat(p.y()).isEqualTo(6);
    }
}