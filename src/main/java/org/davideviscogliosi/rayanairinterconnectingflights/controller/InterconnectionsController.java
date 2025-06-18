package org.davideviscogliosi.rayanairinterconnectingflights.controller;

import org.davideviscogliosi.rayanairinterconnectingflights.model.Interconnection;
import org.davideviscogliosi.rayanairinterconnectingflights.service.InterconnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/api/v1")
@RestController
public class InterconnectionsController {

    @Autowired
    private InterconnectionService interconnectionService;


    @GetMapping("/interconnections")
    public ResponseEntity<List<Interconnection>> getInterconnections(
            @RequestParam String departure,
            @RequestParam String arrival,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureDateTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime arrivalDateTime) {

        if (departure == null || departure.trim().isEmpty() ||
                arrival == null || arrival.trim().isEmpty() ||
                departureDateTime == null || arrivalDateTime == null) {
            return ResponseEntity.badRequest().build();
        }

        if (departureDateTime.isAfter(arrivalDateTime)) {
            return ResponseEntity.badRequest().build();
        }

        if (departure.equals(arrival)) {
            return ResponseEntity.badRequest().build();
        }

        try {
            List<Interconnection> interconnections = interconnectionService.findInterconnections(
                    departure.toUpperCase(),
                    arrival.toUpperCase(),
                    departureDateTime,
                    arrivalDateTime
            );

            return ResponseEntity.ok(interconnections);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
