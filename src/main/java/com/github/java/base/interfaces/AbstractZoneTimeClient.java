package com.github.java.interfaces;

import java.time.ZonedDateTime;

/**
 * @author pengfei.zhao
 * @date 2020/10/13 20:58
 */
public interface AbstractZoneTimeClient extends TimeClient{
    @Override
    public ZonedDateTime getZonedDateTime(String zoneString);
}
