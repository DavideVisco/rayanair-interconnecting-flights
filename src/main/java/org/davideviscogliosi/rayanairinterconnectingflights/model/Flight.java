package org.davideviscogliosi.rayanairinterconnectingflights.model;

import lombok.Data;

@Data
public class Flight {
    private String carrierCode;
    private String number;
    private String departureTime;
    private String arrivalTime;
}
