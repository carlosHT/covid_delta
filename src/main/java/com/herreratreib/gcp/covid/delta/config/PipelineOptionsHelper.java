package com.herreratreib.gcp.covid.delta.config;

import com.herreratreib.gcp.covid.delta.CovidDeltaOptions;
import com.herreratreib.gcp.covid.delta.utils.SerializationUtils;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public final class PipelineOptionsHelper {
    private static final Logger LOG = LoggerFactory.getLogger(PipelineOptionsHelper.class);

    public static CovidDeltaOptions getPipelineOptions(final String... args) {
        final CovidDeltaOptions options = PipelineOptionsFactory.fromArgs(args)
                                                                .withValidation()
                                                                .as(CovidDeltaOptions.class);

        final OptionsBundle optionsBundle = PipelineOptionsHelper.readOptionsBundle(options.getOptionsBundle());
        options.setProjectId(optionsBundle.getProjectId());
        options.setSourceDataset(optionsBundle.getSourceDataset());
        options.setSourceTable(optionsBundle.getSourceTable());

        return options;
    }

    private static OptionsBundle readOptionsBundle(final String optionsBundleName) {
        InputStream inputStream = OptionsBundle.class.getResourceAsStream("/" + optionsBundleName + ".json");

        OptionsBundle optionsBundle = null;
        try {
            optionsBundle = SerializationUtils.getObjectMapper()
                                              .readValue(inputStream,
                                                         OptionsBundle.class);
        } catch (Exception ex) {
            LOG.error("Exception while reading options bundle file.",
                      ex);
        } finally {
            try {
                inputStream.close();
            } catch (IOException ioEx) {
                LOG.error("Exception while closing options bundle input stream.",
                          ioEx);
            }
        }

        return optionsBundle;
    }
}
