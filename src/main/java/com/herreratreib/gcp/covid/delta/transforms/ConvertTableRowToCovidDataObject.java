package com.herreratreib.gcp.covid.delta.transforms;

import com.google.api.services.bigquery.model.TableRow;
import com.herreratreib.gcp.covid.delta.bigquery.SourceQueryColumn;
import com.herreratreib.gcp.covid.delta.model.CovidData;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;

public class ConvertTableRowToCovidDataObject extends DoFn<TableRow, KV<String, CovidData>> {
    private static final Logger LOG = LoggerFactory.getLogger(ConvertTableRowToCovidDataObject.class);

    @ProcessElement
    public void processElement(final ProcessContext context) throws IOException {
        final TableRow row = context.element();

        final CovidData covidData = new CovidData();

        try {
            covidData.setDate((Date) row.get(SourceQueryColumn.DATE));
            covidData.setState((String) row.get(SourceQueryColumn.STATE_NAME));
            covidData.setConfirmedCases((Long) row.get(SourceQueryColumn.CONFIRMED_CASES));
            covidData.setDeaths((Long) row.get(SourceQueryColumn.DEATHS));

        } catch (Exception ex) {
            LOG.error("Invalid row: " + row.toPrettyString());
            return;
        }

        context.output(KV.of(covidData.getState(),
                             covidData));
    }
}
