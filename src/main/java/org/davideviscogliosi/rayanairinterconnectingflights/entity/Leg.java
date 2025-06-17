package org.davideviscogliosi.rayanairinterconnectingflights.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Leg {

    private String departureAirport;
    private String arrivalAirport;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTimeprivate;
}
