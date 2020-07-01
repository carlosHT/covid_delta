package com.herreratreib.gcp.covid.delta.model;

import java.util.Date;
import java.util.Map;

public class CovidDeltaPerState {
    private String state;
    private Date initialDate;
    private Date finalDate;
    private Map<Integer, Long> confirmedCasesDeltaPerWeek;
    private Map<Integer, Long> deathsDeltaPerWeek;

    public String getState() {
        return state;
    }

    public void setState(final String state) {
        this.state = state;
    }

    public Date getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(final Date initialDate) {
        this.initialDate = initialDate;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(final Date finalDate) {
        this.finalDate = finalDate;
    }

    public Map<Integer, Long> getConfirmedCasesDeltaPerWeek() {
        return confirmedCasesDeltaPerWeek;
    }

    public void setConfirmedCasesDeltaPerWeek(final Map<Integer, Long> confirmedCasesDeltaPerWeek) {
        this.confirmedCasesDeltaPerWeek = confirmedCasesDeltaPerWeek;
    }

    public Map<Integer, Long> getDeathsDeltaPerWeek() {
        return deathsDeltaPerWeek;
    }

    public void setDeathsDeltaPerWeek(final Map<Integer, Long> deathsDeltaPerWeek) {
        this.deathsDeltaPerWeek = deathsDeltaPerWeek;
    }

    @Override
    public String toString() {
        return "CovidDeltaPerState{"
               + "state='" + state + '\''
               + ", initialDate=" + initialDate
               + ", finalDate=" + finalDate
               + ", confirmedCasesDeltaPerWeek=" + confirmedCasesDeltaPerWeek
               + ", deathsDeltaPerWeek=" + deathsDeltaPerWeek
               + '}';
    }
}
