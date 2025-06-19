package org.davideviscogliosi.rayanairinterconnectingflights.service;

import lombok.extern.slf4j.Slf4j;
import org.davideviscogliosi.rayanairinterconnectingflights.model.Interconnection;
import org.davideviscogliosi.rayanairinterconnectingflights.model.Leg;
import org.davideviscogliosi.rayanairinterconnectingflights.model.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class InterconnectionService {

    private static final int MINIMUM_CONNECTION_TIME_HOURS = 2;

    @Autowired
    private RouteService routeService;
    @Autowired
    private FlightScheduleService flightScheduleService;

    public List<Interconnection> findInterconnections(String departure, String arrival,
                                                      LocalDateTime departureDateTime,
                                                      LocalDateTime arrivalDateTime) {
        List<Interconnection> interconnections = new ArrayList<>();

        interconnections.addAll(findDirectFlights(departure, arrival, departureDateTime, arrivalDateTime));

        interconnections.addAll(findOneStopFlights(departure, arrival, departureDateTime, arrivalDateTime));

        return interconnections;
    }

    private List<Interconnection> findDirectFlights(String departure, String arrival,
                                                          LocalDateTime departureDateTime,
                                                          LocalDateTime arrivalDateTime) {
        List<Interconnection> directFlights = new ArrayList<>();
        List<Route> directRoutes = routeService.getDirectRoutes(departure, arrival);

        if (!directRoutes.isEmpty()) {
            List<Leg> flightLegs = flightScheduleService.getFlightLegs(
                    departure, arrival, departureDateTime, arrivalDateTime);

            for (Leg leg : flightLegs) {
                Interconnection interconnection = new Interconnection(0, List.of(leg));
                directFlights.add(interconnection);
            }
        }

        return directFlights;
    }

    private List<Interconnection> findOneStopFlights(String departure, String arrival,
                                                           LocalDateTime departureDateTime,
                                                           LocalDateTime arrivalDateTime) {
        List<Interconnection> oneStopFlights = new ArrayList<>();

        List<Route> departureLegRoutes = routeService.getRoutesFromAirport(departure);
        List<Route> arrivalLegRoutes = routeService.getRoutesToAirport(arrival);

        for (Route firstRoute : departureLegRoutes) {
            String intermediateAirport = firstRoute.getAirportTo();

            boolean hasSecondLeg = arrivalLegRoutes.stream()
                    .anyMatch(route -> route.getAirportFrom().equals(intermediateAirport));

            if (hasSecondLeg && !intermediateAirport.equals(departure) && !intermediateAirport.equals(arrival)) {
                List<Leg> firstLegFlights = flightScheduleService.getFlightLegs(
                        departure, intermediateAirport, departureDateTime, arrivalDateTime);

                for (Leg firstLeg : firstLegFlights) {
                    LocalDateTime firstLegArrival = firstLeg.getArrivalDateTime();
                    LocalDateTime secondLegEarliestDeparture = firstLegArrival.plusHours(MINIMUM_CONNECTION_TIME_HOURS);

                    List<Leg> secondLegFlights = flightScheduleService.getFlightLegs(
                            intermediateAirport, arrival, secondLegEarliestDeparture, arrivalDateTime);

                    for (Leg secondLeg : secondLegFlights) {
                        Interconnection interconnection = new Interconnection(
                                1, List.of(firstLeg, secondLeg));
                        oneStopFlights.add(interconnection);
                    }
                }
            }
        }

        return oneStopFlights;
    }


}
