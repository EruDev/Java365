package com.github.java.interfaces;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author pengfei.zhao
 * @date 2020/10/13 20:34
 */
public interface TimeClient {
    void setTime(int hour, int minute, int second);

    void setDate(int year, int month, int day);

    void setDateAndTime(int day, int month, int year, int hour, int minute, int second);

    LocalDateTime getLocalDateTime();

    static ZoneId getZoneId(String zoneString) {
        try {
            return ZoneId.of(zoneString);
        } catch (DateTimeException ex) {
            System.err.println("Invalid time zone: " + zoneString +
                    "; using default time zone instead");
            return ZoneId.systemDefault();
        }
    }

    default ZonedDateTime getZonedDateTime(String zoneString) {
        return ZonedDateTime.of(getLocalDateTime(), getZoneId(zoneString));
    }
}
