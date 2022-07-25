package com.connorphee.githubuserinfo.converter;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DateConverterTest {

    @Test
    public void returnsFormattedString() {
        DateConverter dateConverter = new DateConverter();
        String result = dateConverter.fromISO8601("2011-01-25T18:44:36Z");
        assertThat(result).isEqualTo("2011-01-25 18:44:36");
    }

    @Test
    public void throwsGivenInvalidString() {
        DateConverter dateConverter = new DateConverter();
        assertThat(dateConverter.fromISO8601("aninvalidstring")).isEqualTo("");
    }

    @Test
    public void returnsEmptyStringGivenNull() {
        DateConverter dateConverter = new DateConverter();
        assertThat(dateConverter.fromISO8601(null)).isEqualTo("");
    }

    @Test
    public void returnsEmptyStringGivenEmptyString() {
        DateConverter dateConverter = new DateConverter();
        assertThat(dateConverter.fromISO8601("")).isEqualTo("");
    }
}
