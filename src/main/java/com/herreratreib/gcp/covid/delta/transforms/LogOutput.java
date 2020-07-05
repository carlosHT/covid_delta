package com.herreratreib.gcp.covid.delta.transforms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.herreratreib.gcp.covid.delta.model.CovidDeltaPerState;
import com.herreratreib.gcp.covid.delta.utils.SerializationUtils;
import org.apache.beam.sdk.transforms.DoFn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogOutput extends DoFn<CovidDeltaPerState, Void> {
    private static final Logger LOG = LoggerFactory.getLogger(LogOutput.class);

    @ProcessElement
    public void processElement(final ProcessContext context) throws JsonProcessingException {
        LOG.info("Final output: " + SerializationUtils.getObjectMapper()
                                                      .writeValueAsString(context.element()));
    }
}
