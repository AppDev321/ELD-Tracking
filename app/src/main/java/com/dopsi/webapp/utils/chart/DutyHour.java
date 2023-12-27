package com.dopsi.webapp.utils.chart;



public class DutyHour {
    private String dutyType;
    private long startTime;
    private long endTime;

    public DutyHour(String dutyType, long startTime, long endTime) {
        this.dutyType = dutyType;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDutyType() {
        return dutyType;
    }

    public void setDutyType(String dutyType) {
        this.dutyType = dutyType;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
