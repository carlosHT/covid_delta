package com.herreratreib.gcp.covid.delta;

import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;

public interface CovidDeltaOptions extends PipelineOptions {
    @Description("Project Id in GCP")
    String getProjectId();
    void setProjectId(String value);

    @Description("Source dataset in BigQuery")
    String getSourceDataset();
    void setSourceDataset(String value);

    @Description("Source table in BigQuery")
    String getSourceTable();
    void setSourceTable(String value);
}
