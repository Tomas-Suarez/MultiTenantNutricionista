package com.devtomi.nutri.modules.dashboard.dto.response;

public class DailyWeighingResponse {

    private String day;
    private long count;

    public DailyWeighingResponse() {
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
