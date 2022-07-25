package com.connorphee.githubuserinfo.converter;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class DateConverter {

    private final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public String fromISO8601(String input) {
        if(input == null || input.isBlank()) {
            return "";
        }

        try {
            return Instant.parse(input)
                    .atOffset(ZoneOffset.UTC)
                    .format(
                            DateTimeFormatter.ofPattern(DATE_PATTERN)
                    );
        } catch (DateTimeParseException e) {
            // Something else should happen here, tracking bad date strings etc..
            // but some response without a create date is better than none.
            return "";
        }
    }
}
