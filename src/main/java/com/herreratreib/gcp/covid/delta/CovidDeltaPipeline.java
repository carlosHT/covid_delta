package com.herreratreib.gcp.covid.delta;

import com.google.api.services.bigquery.model.TableRow;
import com.herreratreib.gcp.covid.delta.config.PipelineOptionsHelper;
import com.herreratreib.gcp.covid.delta.model.CovidData;
import com.herreratreib.gcp.covid.delta.bigquery.BigQueryHelper;
import com.herreratreib.gcp.covid.delta.config.TableRowCoder;
import com.herreratreib.gcp.covid.delta.model.CovidDeltaPerState;
import com.herreratreib.gcp.covid.delta.transforms.CalculateCovidDeltaPerState;
import com.herreratreib.gcp.covid.delta.transforms.ConvertTableRowToCovidDataObject;
import com.herreratreib.gcp.covid.delta.transforms.LogOutput;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.transforms.GroupByKey;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;

public class CovidDeltaPipeline {
    public static void main(final String[] args) {
        final CovidDeltaOptions options = PipelineOptionsHelper.getPipelineOptions(args);
        final Pipeline pipeline = Pipeline.create(options);

        // Registering custom Avro coder for TableRow class.
        pipeline.getCoderRegistry()
                .registerCoderForClass(TableRow.class,
                                       new TableRowCoder());

        //****************************************************************************************************************
        //******************************************* [READING FROM BIGQUERY] ********************************************
        //****************************************************************************************************************
        final PCollection<KV<String, CovidData>> incomingData = pipeline.apply(BigQueryIO.readTableRows()
                                                                                         .fromQuery(BigQueryHelper.buildSourceQuery(options))
                                                                                         .usingStandardSql()
                                                                                         .withoutValidation()
                                                                                         .withCoder(new TableRowCoder()))
                                                                        .apply(ParDo.of(new ConvertTableRowToCovidDataObject()));

        //****************************************************************************************************************
        //********************************** [GROUPING BY STATE AND CALCULATING DELTA] ***********************************
        //****************************************************************************************************************
        final PCollection<CovidDeltaPerState> covidDeltaPerStateData = incomingData.apply(GroupByKey.create())
                                                                                   .apply(ParDo.of(new CalculateCovidDeltaPerState()));

        //****************************************************************************************************************
        //*************************************** [OUTPUT (FOR NOW ONLY LOGGING)] ****************************************
        //****************************************************************************************************************
        covidDeltaPerStateData.apply(ParDo.of(new LogOutput()));

        pipeline.run();
    }
}
