package com.herreratreib.gcp.covid.delta.bigquery;

import com.herreratreib.gcp.covid.delta.CovidDeltaOptions;

public final class BigQueryHelper {
    public static String buildSourceQuery(final CovidDeltaOptions options) {
        return "SELECT * FROM `bigquery-public-data."
               + options.getSourceDataset()
               + "."
               + options.getSourceTable()
               + "`";
    }
}
