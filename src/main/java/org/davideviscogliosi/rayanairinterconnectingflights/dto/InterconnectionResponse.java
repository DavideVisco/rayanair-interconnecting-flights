package org.davideviscogliosi.rayanairinterconnectingflights.dto;

import lombok.Data;
import org.davideviscogliosi.rayanairinterconnectingflights.entity.Leg;

import java.util.List;

@Data
public class InterconnectionResponse {

    private int stops;
    private List<Leg> legs;
}
