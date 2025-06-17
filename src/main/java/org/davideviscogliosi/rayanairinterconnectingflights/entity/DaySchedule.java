package org.davideviscogliosi.rayanairinterconnectingflights.entity;

import lombok.Data;
import java.util.List;

@Data
public class DaySchedule {

    private int day;
    private List<Flight> flights;
}
