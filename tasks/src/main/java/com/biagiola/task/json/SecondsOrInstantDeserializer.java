package com.biagiola.task.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.time.format.DateTimeParseException;

public class SecondsOrInstantDeserializer extends JsonDeserializer<Instant> {

    private final Clock clock;

    public SecondsOrInstantDeserializer() {
        this.clock = Clock.systemUTC();
    }

    // Optional: for tests
    public SecondsOrInstantDeserializer(Clock clock) {
        this.clock = clock;
    }

    @Override
    public Instant deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonToken token = p.currentToken();

        if (token == JsonToken.VALUE_NUMBER_INT) {
            long seconds = p.getLongValue();
            validateSeconds(p, seconds);
            return Instant.now(clock).plusSeconds(seconds);
        }

        if (token == JsonToken.VALUE_STRING) {
            String value = p.getText() == null ? "" : p.getText().trim();

            if (value.matches("^\\d+$")) {
                long seconds = Long.parseLong(value);
                validateSeconds(p, seconds);
                return Instant.now(clock).plusSeconds(seconds);
            }

            try {
                return Instant.parse(value);
            } catch (DateTimeParseException e) {
                throw InvalidFormatException.from(
                        p,
                        "executeAt must be ISO-8601 (e.g. 2026-01-10T15:30:00Z) or seconds-from-now (e.g. 20)",
                        value,
                        Instant.class
                );
            }
        }

        throw InvalidFormatException.from(p, "Invalid executeAt value", p.getText(), Instant.class);
    }

    private void validateSeconds(JsonParser p, long seconds) throws InvalidFormatException {
        if (seconds < 0) {
            throw InvalidFormatException.from(p, "executeAt seconds must be >= 0", seconds, Instant.class);
        }
    }
}
