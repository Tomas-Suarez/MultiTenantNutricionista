package com.devtomi.nutri.modules.dashboard.dto.response;

import java.util.List;

public class DashboardSummaryResponse {

    private long registeredPatients;
    private long linkedPatients;
    private long weighings;
    private String period;
    private String periodStart;
    private String periodEnd;
    private List<DailyWeighingResponse> weighingsByDay;

    public DashboardSummaryResponse() {
    }

    public long getRegisteredPatients() {
        return registeredPatients;
    }

    public void setRegisteredPatients(long registeredPatients) {
        this.registeredPatients = registeredPatients;
    }

    public long getLinkedPatients() {
        return linkedPatients;
    }

    public void setLinkedPatients(long linkedPatients) {
        this.linkedPatients = linkedPatients;
    }

    public long getWeighings() {
        return weighings;
    }

    public void setWeighings(long weighings) {
        this.weighings = weighings;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(String periodStart) {
        this.periodStart = periodStart;
    }

    public String getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(String periodEnd) {
        this.periodEnd = periodEnd;
    }

    public List<DailyWeighingResponse> getWeighingsByDay() {
        return weighingsByDay;
    }

    public void setWeighingsByDay(List<DailyWeighingResponse> weighingsByDay) {
        this.weighingsByDay = weighingsByDay;
    }
}
