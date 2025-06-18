package org.davideviscogliosi.rayanairinterconnectingflights.model;

import lombok.Data;
import java.util.List;

@Data
public class DaySchedule {

    private int day;
    private List<Flight> flights;
}
