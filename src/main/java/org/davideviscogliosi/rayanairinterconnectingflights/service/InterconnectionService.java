package org.davideviscogliosi.rayanairinterconnectingflights.service;

import lombok.extern.slf4j.Slf4j;
import org.davideviscogliosi.rayanairinterconnectingflights.client.impl.RouteAndScheduleClientImpl;
import org.davideviscogliosi.rayanairinterconnectingflights.dto.InterconnectionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class InterconnectionService {

    @Autowired
    private RouteAndScheduleClientImpl routeAndScheduleClient;


//    public List<InterconnectionResponse> getFlights(String departure,
//                                                    String arrival,
//                                                    LocalDateTime departureDateTime,
//                                                    LocalDateTime arrivalDateTime){
//
//
//    }


}
