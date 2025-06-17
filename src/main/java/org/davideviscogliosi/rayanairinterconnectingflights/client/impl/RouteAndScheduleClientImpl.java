package org.davideviscogliosi.rayanairinterconnectingflights.client.impl;

import lombok.extern.slf4j.Slf4j;
import org.davideviscogliosi.rayanairinterconnectingflights.client.RouteAndScheduleClient;
import org.davideviscogliosi.rayanairinterconnectingflights.entity.Route;
import org.davideviscogliosi.rayanairinterconnectingflights.entity.ScheduleResponse;
import org.davideviscogliosi.rayanairinterconnectingflights.exception.RayanairException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RouteAndScheduleClientImpl {


    @Autowired
    private RouteAndScheduleClient routeAndScheduleClient;


    public List<Route> getFlightsRoutes(){

        try {
            return routeAndScheduleClient.getFlightRoutes();
        } catch (Exception e) {
            log.error("Error retrieving Ryanair routes :", e);
            throw new RayanairException("Failed to retrieve Ryanair routes", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ScheduleResponse getSchedules(String departure, String arrival, int year, int month) {

        try {
            return routeAndScheduleClient.getSchedules(departure, arrival, year, month);
        } catch (Exception e) {
            log.error("Error retrieving schedules :", e);
            throw new RayanairException("Failed to retrieve Ryanair schedules", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
