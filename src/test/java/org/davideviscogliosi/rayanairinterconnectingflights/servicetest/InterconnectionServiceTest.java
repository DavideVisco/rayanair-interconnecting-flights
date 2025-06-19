package org.davideviscogliosi.rayanairinterconnectingflights.servicetest;

import net.bytebuddy.asm.Advice;
import org.davideviscogliosi.rayanairinterconnectingflights.model.Interconnection;
import org.davideviscogliosi.rayanairinterconnectingflights.model.Leg;
import org.davideviscogliosi.rayanairinterconnectingflights.model.Route;
import org.davideviscogliosi.rayanairinterconnectingflights.service.FlightScheduleService;
import org.davideviscogliosi.rayanairinterconnectingflights.service.InterconnectionService;
import org.davideviscogliosi.rayanairinterconnectingflights.service.RouteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InterconnectionServiceTest {

    @Mock
    private RouteService routeService;

    @Mock
    private FlightScheduleService flightScheduleService;

    @InjectMocks
    private InterconnectionService interconnectionService;

    @Test
    void shouldFindDirectFlights() {

        Route directRoute = new Route("AAR", "ZAD", null, false, false, "RYANAIR", null,null);
        Leg directLeg = new Leg("AAR",
                "ZAD",
                LocalDateTime.of(2025,6,1,7,0),
                LocalDateTime.of(2025,6,30,21,0));

        when(routeService.getDirectRoutes("AAR", "ZAD")).thenReturn(List.of(directRoute));
        when(flightScheduleService.getFlightLegs(eq("AAR"), eq("ZAD"), any(), any()))
                .thenReturn(List.of(directLeg));

        LocalDateTime departureDateTime = LocalDateTime.of(2025,6,1,7,0);
        LocalDateTime arrivalDateTime = LocalDateTime.of(2025,6,30,21,0);

        List<Interconnection> interconnections = interconnectionService.findInterconnections(
                "AAR", "ZAD", departureDateTime, arrivalDateTime);


        assertEquals(1, interconnections.size());
        assertEquals(0, interconnections.get(0).getStops());
        assertEquals(1, interconnections.get(0).getLegs().size());
    }

    @Test
    void shouldFindOneStopFlights() {

        Route firstLegRoute = new Route("FCO", "BCN", null, false, false, "RYANAIR", null,null);
        Route secondLegRoute = new Route("BCN", "SVQ", null, false, false, "RYANAIR", null,null);

        Leg firstLeg = new Leg("FCO", "BCN", LocalDateTime.of(2025,6,1,6,25),LocalDateTime.of(2025,6,1,7,35));
        Leg secondLeg = new Leg("BCN", "SVQ", LocalDateTime.of(2025,6,1,9,50),LocalDateTime.of(2025,6,1,13,20));

        when(routeService.getDirectRoutes("FCO", "SVQ")).thenReturn(List.of());
        when(routeService.getRoutesFromAirport("FCO")).thenReturn(List.of(firstLegRoute));
        when(routeService.getRoutesToAirport("SVQ")).thenReturn(List.of(secondLegRoute));

        when(flightScheduleService.getFlightLegs(eq("FCO"), eq("BCN"), any(), any()))
                .thenReturn(List.of(firstLeg));
        when(flightScheduleService.getFlightLegs(eq("BCN"), eq("SVQ"), any(), any()))
                .thenReturn(List.of(secondLeg));

        LocalDateTime departureDateTime = LocalDateTime.of(2018, 3, 1, 7, 0);
        LocalDateTime arrivalDateTime = LocalDateTime.of(2018, 3, 3, 21, 0);

        List<Interconnection> interconnections = interconnectionService.findInterconnections(
                "FCO", "SVQ", departureDateTime, arrivalDateTime);

        assertEquals(1, interconnections.size());
        assertEquals(1, interconnections.get(0).getStops());
        assertEquals(2, interconnections.get(0).getLegs().size());
    }

    @Test
    void shouldReturnEmptyWhenNoRoutesFound() {
        when(routeService.getDirectRoutes("FCO", "SVQ")).thenReturn(List.of());
        when(routeService.getRoutesFromAirport("FCO")).thenReturn(List.of());
        when(routeService.getRoutesToAirport("SVQ")).thenReturn(List.of());

        LocalDateTime departureDateTime = LocalDateTime.of(2025,6,1,6,25);
        LocalDateTime arrivalDateTime = LocalDateTime.of(2025,6,30,21,0);

        List<Interconnection> interconnections = interconnectionService.findInterconnections(
                "FCO", "SVQ", departureDateTime, arrivalDateTime);

        assertTrue(interconnections.isEmpty());
    }
}