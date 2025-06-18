package org.davideviscogliosi.rayanairinterconnectingflights.model;

import lombok.Data;

import java.util.List;

@Data
public class Route {

    private String airportFrom;
    private String airportTo;
    private String connectingAirport;
    private boolean newRoute;
    private boolean seasonalRoute;
    private String operator;
    private List<String> similarArrivalAirportCodes;
    private List<String> tags;


}
