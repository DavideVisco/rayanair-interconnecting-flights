package org.davideviscogliosi.rayanairinterconnectingflights.util;

import lombok.extern.slf4j.Slf4j;
import org.davideviscogliosi.rayanairinterconnectingflights.model.Route;
import org.davideviscogliosi.rayanairinterconnectingflights.exception.RayanairException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class RouteFilteringUtils {


    public static List<Route> validateRoute(List<Route> routesToFilter,String operator){

        try {
            return routesToFilter.stream()
                    .filter(route -> operator.equals(route.getOperator()) && route.getConnectingAirport() == null)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Error filtering Ryanair routes :", e);
            throw new RayanairException("Failed to filter Ryanair routes", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
