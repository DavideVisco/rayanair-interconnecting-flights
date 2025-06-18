package org.davideviscogliosi.rayanairinterconnectingflights.service;

import org.davideviscogliosi.rayanairinterconnectingflights.client.impl.RouteAndScheduleClientImpl;
import org.davideviscogliosi.rayanairinterconnectingflights.model.Route;
import org.davideviscogliosi.rayanairinterconnectingflights.util.RouteFilteringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class RouteService {

    @Autowired
    private RouteAndScheduleClientImpl ryanairApiService;

    public List<Route> getValidRoutes() {
        return RouteFilteringUtils.validateRoute(ryanairApiService.getFlightsRoutes(),"RYANAIR");
    }

    public List<Route> getDirectRoutes(String departure, String arrival) {
        return getValidRoutes()
                .stream()
                .filter(route -> route.getAirportFrom().equals(departure) &&
                        route.getAirportTo().equals(arrival))
                .collect(Collectors.toList());
    }

    public List<Route> getRoutesFromAirport(String airport) {
        return getValidRoutes()
                .stream()
                .filter(route -> route.getAirportFrom().equals(airport))
                .collect(Collectors.toList());
    }

    public List<Route> getRoutesToAirport(String airport) {
        return getValidRoutes()
                .stream()
                .filter(route -> route.getAirportTo().equals(airport))
                .collect(Collectors.toList());
    }

}
