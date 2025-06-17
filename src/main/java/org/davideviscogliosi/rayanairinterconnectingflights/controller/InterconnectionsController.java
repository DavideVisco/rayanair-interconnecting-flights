package org.davideviscogliosi.rayanairinterconnectingflights.controller;

import org.davideviscogliosi.rayanairinterconnectingflights.entity.Interconnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/interconnections")
@RestController
public class InterconnectionsController {


//    @GetMapping()
//    public List<Interconnection> getInterconnections(@RequestParam String departure,
//                                                     @RequestParam String arrival,
//                                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureDateTime,
//                                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime arrivalDateTime) {
//
//
//
//
//    }
}
