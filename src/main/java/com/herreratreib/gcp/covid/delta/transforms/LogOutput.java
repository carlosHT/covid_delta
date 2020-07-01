package com.herreratreib.gcp.covid.delta.transforms;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.herreratreib.gcp.covid.delta.model.CovidDeltaPerState;
import org.apache.beam.sdk.transforms.DoFn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;

public class LogOutput extends DoFn<CovidDeltaPerState, Void> {
    private static final Logger LOG = LoggerFactory.getLogger(LogOutput.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    }

    @ProcessElement
    public void processElement(final ProcessContext context) throws JsonProcessingException {
        LOG.info("Final output: " + OBJECT_MAPPER.writeValueAsString(context.element()));
    }
}
