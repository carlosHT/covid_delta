package com.herreratreib.gcp.covid.delta.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OptionsBundle {
    @JsonProperty("projectId")
    private String projectId;
    @JsonProperty("sourceDataset")
    private String sourceDataset;
    @JsonProperty("sourceTable")
    private String sourceTable;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(final String projectId) {
        this.projectId = projectId;
    }

    public String getSourceDataset() {
        return sourceDataset;
    }

    public void setSourceDataset(final String sourceDataset) {
        this.sourceDataset = sourceDataset;
    }

    public String getSourceTable() {
        return sourceTable;
    }

    public void setSourceTable(final String sourceTable) {
        this.sourceTable = sourceTable;
    }

    @Override
    public String toString() {
        return "CovidDeltaOptionsBundle{"
               + "projectId='" + projectId + '\''
               + ", sourceDataset='" + sourceDataset + '\''
               + ", sourceTable='" + sourceTable + '\''
               + '}';
    }
}
