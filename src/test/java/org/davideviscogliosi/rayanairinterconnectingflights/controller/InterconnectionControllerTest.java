package org.davideviscogliosi.rayanairinterconnectingflights.controller;



import org.davideviscogliosi.rayanairinterconnectingflights.model.Interconnection;
import org.davideviscogliosi.rayanairinterconnectingflights.model.Leg;
import org.davideviscogliosi.rayanairinterconnectingflights.service.InterconnectionService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;


@WebMvcTest(InterconnectionsController.class)
class InterconnectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InterconnectionService interconnectionService;


    @Test
    void shouldReturnInterconnections() throws Exception {

        Interconnection interconnection = new Interconnection(0, List.of(new Leg("AAR",
                "ZAD",
                LocalDateTime.of(2025,6,1,7,0),
                LocalDateTime.of(2025,6,30,21,0))));

        Mockito.when(interconnectionService.findInterconnections(
                ArgumentMatchers.eq("AAR"), ArgumentMatchers.eq("ZAD"), ArgumentMatchers.any(LocalDateTime.class), ArgumentMatchers.any(LocalDateTime.class)))
                .thenReturn(List.of(interconnection));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/interconnections")
                        .param("departure", "AAR")
                        .param("arrival", "ZAD")
                        .param("departureDateTime", "2025-06-01T07:00")
                        .param("arrivalDateTime", "2025-06-30T21:00"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].stops").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].legs[0].departureAirport").value("AAR"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].legs[0].arrivalAirport").value("ZAD"));

    }

    @Test
    void shouldReturn400WhenMissingParameters() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/interconnections")
                        .param("departure", "ZAD")
                        .param("arrival", "AAR")
                        .param("arrivalDateTime", "2018-03-03T21:00"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenDepartureDateAfterArrivalDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/interconnections")
                        .param("departure", "ZAD")
                        .param("arrival", "AAR")
                        .param("departureDateTime", "2018-03-03T21:00")
                        .param("arrivalDateTime", "2018-03-01T07:00"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
