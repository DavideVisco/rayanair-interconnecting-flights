package org.davideviscogliosi.rayanairinterconnectingflights.model;

import lombok.Data;

import java.util.List;

@Data
public class ScheduleResponse {
    private int month;
    private List<DaySchedule> days;
}
