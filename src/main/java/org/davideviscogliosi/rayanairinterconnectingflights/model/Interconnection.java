package org.davideviscogliosi.rayanairinterconnectingflights.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class Interconnection {
    private int stops;
    private List<Leg> legs;
}
