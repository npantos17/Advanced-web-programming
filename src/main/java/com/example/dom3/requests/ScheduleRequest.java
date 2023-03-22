package com.example.dom3.requests;

import lombok.Data;

@Data
public class ScheduleRequest {
    private Long id;
    private String date;
    private String time;
    private String action;
}
