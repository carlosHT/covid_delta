package com.herreratreib.gcp.covid.delta.transforms;

import com.google.api.services.bigquery.model.TableRow;
import com.herreratreib.gcp.covid.delta.bigquery.SourceQueryColumn;
import com.herreratreib.gcp.covid.delta.model.CovidData;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertTableRowToCovidDataObject extends DoFn<TableRow, KV<String, CovidData>> {
    private static final Logger LOG = LoggerFactory.getLogger(ConvertTableRowToCovidDataObject.class);
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");


    @ProcessElement
    public void processElement(final ProcessContext context) throws Exception {
        final TableRow row = context.element();

        final CovidData covidData = new CovidData();

        try {
            covidData.setDate(extractDate(row.get(SourceQueryColumn.DATE)));
            covidData.setState((String) row.get(SourceQueryColumn.STATE_NAME));
            covidData.setConfirmedCases(extractLong(row.get(SourceQueryColumn.CONFIRMED_CASES)));
            covidData.setDeaths(extractLong(row.get(SourceQueryColumn.DEATHS)));

        } catch (Exception ex) {
            LOG.error("Invalid row: " + row.toPrettyString(),
                      ex);
            return;
        }

        context.output(KV.of(covidData.getState(),
                             covidData));
    }

    //****************************************************************************************************************
    //********************************************** [HELPER METHODS] ************************************************
    //****************************************************************************************************************
    private static Date extractDate(final Object value) throws ParseException {
        return DATE_FORMATTER.parse((String) value);
    }

    private static Long extractLong(final Object value) {
        return Long.valueOf((String) value);
    }
}
