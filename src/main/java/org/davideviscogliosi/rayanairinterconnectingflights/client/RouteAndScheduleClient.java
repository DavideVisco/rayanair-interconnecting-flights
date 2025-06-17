package org.davideviscogliosi.rayanairinterconnectingflights.client;

import org.davideviscogliosi.rayanairinterconnectingflights.entity.Route;
import org.davideviscogliosi.rayanairinterconnectingflights.entity.ScheduleResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "routeClient",url = "${base.url.rayanair}")
public interface RouteAndScheduleClient {


    @GetMapping("/views/locate/3/routes")
    List<Route> getFlightRoutes();


    @GetMapping("/timtbl/3/schedules/{departure}/{arrival}/years/{year}/months/{month}")
    ScheduleResponse getSchedules(
            @PathVariable("departure") String departure,
            @PathVariable("arrival") String arrival,
            @PathVariable("year") int year,
            @PathVariable("month") int month
    );

}
