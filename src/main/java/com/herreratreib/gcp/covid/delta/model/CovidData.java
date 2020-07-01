package com.herreratreib.gcp.covid.delta.model;

import java.util.Date;

public class CovidData {
    private Date date;
    private String state;
    private Long confirmedCases;
    private Long deaths;

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(final String state) {
        this.state = state;
    }

    public Long getConfirmedCases() {
        return confirmedCases;
    }

    public void setConfirmedCases(final Long confirmedCases) {
        this.confirmedCases = confirmedCases;
    }

    public Long getDeaths() {
        return deaths;
    }

    public void setDeaths(final Long deaths) {
        this.deaths = deaths;
    }

    @Override
    public String toString() {
        return "CovidData{"
               + "date=" + date
               + ", state='" + state + '\''
               + ", confirmedCases=" + confirmedCases
               + ", deaths=" + deaths
               + '}';
    }
}
