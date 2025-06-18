package org.davideviscogliosi.rayanairinterconnectingflights.service;

import org.davideviscogliosi.rayanairinterconnectingflights.client.impl.RouteAndScheduleClientImpl;
import org.davideviscogliosi.rayanairinterconnectingflights.model.DaySchedule;
import org.davideviscogliosi.rayanairinterconnectingflights.model.Flight;
import org.davideviscogliosi.rayanairinterconnectingflights.model.Leg;
import org.davideviscogliosi.rayanairinterconnectingflights.model.ScheduleResponse;
import org.davideviscogliosi.rayanairinterconnectingflights.util.LocalDateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FlightScheduleService {

    @Autowired
    private RouteAndScheduleClientImpl routeAndScheduleClient;

    public List<Leg> getFlightLegs(String departure, String arrival,
                                         LocalDateTime departureDateTime,
                                         LocalDateTime arrivalDateTime) {
        List<Leg> flightLegs = new ArrayList<>();

        YearMonth startMonth = YearMonth.from(departureDateTime.toLocalDate());
        YearMonth endMonth = YearMonth.from(arrivalDateTime.toLocalDate());

        for(YearMonth month = startMonth; !month.isAfter(endMonth); month = month.plusMonths(1)) {
            Optional<ScheduleResponse> scheduleResponse = routeAndScheduleClient.getSchedules(departure, arrival, startMonth.getYear(), startMonth.getMonthValue());
            if(scheduleResponse.isEmpty()) {
                startMonth.plusMonths(1);
                continue;
            }
            flightLegs.addAll(extractFlightLegsFromSchedule(scheduleResponse.get(),departure,arrival,departureDateTime,arrivalDateTime));
            startMonth.plusMonths(1);

        }



        return flightLegs;
    }

    private List<Leg> extractFlightLegsFromSchedule(ScheduleResponse schedule, String departure, String arrival,
                                                    LocalDateTime departureDateTime, LocalDateTime arrivalDateTime) {
        List<Leg> flightLegs = new ArrayList<>();

        for (DaySchedule day : schedule.getDays()) {

            if(day.getFlights() != null && !day.getFlights().isEmpty()) {

                LocalDate flightDate = LocalDate.of(
                        departureDateTime.getYear(),
                        schedule.getMonth(),
                        day.getDay()
                );
                for (Flight flight : day.getFlights()) {
                    LocalDateTime flightDepartureDateTime = LocalDateTimeUtil.createDateTime(flightDate, flight.getDepartureTime());
                    LocalDateTime flightArrivalDateTime = LocalDateTimeUtil.createDateTime(flightDate, flight.getArrivalTime());

                    if (flightArrivalDateTime.isBefore(flightDepartureDateTime)) {
                        flightArrivalDateTime = flightArrivalDateTime.plusDays(1);
                    }

                    if (isFlightValid(flightDepartureDateTime, flightArrivalDateTime, departureDateTime, arrivalDateTime)) {
                        Leg leg = new Leg(
                                departure,
                                arrival,
                                flightDepartureDateTime,
                                flightArrivalDateTime
                        );
                        flightLegs.add(leg);
                    }
                }

            }
        }

        return flightLegs;
    }


    private boolean isFlightValid(LocalDateTime flightDeparture, LocalDateTime flightArrival,
                                  LocalDateTime requestedDeparture, LocalDateTime requestedArrival) {
        return !flightDeparture.isBefore(requestedDeparture) &&
                !flightArrival.isAfter(requestedArrival);
    }
}
