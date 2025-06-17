package org.davideviscogliosi.rayanairinterconnectingflights.entity;

import lombok.Data;

import java.util.List;

@Data
public class Interconnection {
    private int stops;
    private List<Leg> legs;
}
