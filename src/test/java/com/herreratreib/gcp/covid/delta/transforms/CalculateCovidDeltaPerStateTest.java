package com.herreratreib.gcp.covid.delta.transforms;

import com.herreratreib.gcp.covid.delta.model.CovidData;
import com.herreratreib.gcp.covid.delta.model.CovidDeltaPerState;
import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.IterableCoder;
import org.apache.beam.sdk.coders.KvCoder;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.testing.TestPipeline;
import org.apache.beam.sdk.testing.TestStream;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.SerializableFunction;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.logging.log4j.util.Strings;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;

@RunWith(JUnit4.class)
public class CalculateCovidDeltaPerStateTest {
    @Rule
    public final transient TestPipeline testPipeline = TestPipeline.create();

    @Test
    public void testTransform() {
        //given
        //TODO: create incoming data.

        final TestStream<KV<String, Iterable<CovidData>>> incomingCovidData = TestStream.create(KvCoder.of(AvroCoder.of(String.class),
                                                                                                           IterableCoder.of(AvroCoder.of(CovidData.class))))
                                                                                        .addElements(KV.of(Strings.EMPTY,
                                                                                                           Arrays.asList(new CovidData())))
                                                                                        .advanceWatermarkToInfinity();

        //when
        final PCollection<CovidDeltaPerState> output = testPipeline.apply(incomingCovidData)
                                                                   .apply(ParDo.of(new CalculateCovidDeltaPerState()));

        //then
        PAssert.that(output)
               .satisfies((SerializableFunction<Iterable<CovidDeltaPerState>, Void>) iterable -> {
                   final CovidDeltaPerState covidDeltaPerState = iterable.iterator()
                                                                         .next();

                   //TODO: implement assertions.

                   return null;
               });

        testPipeline.run();
    }
}
