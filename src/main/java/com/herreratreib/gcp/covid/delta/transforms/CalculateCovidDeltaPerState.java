package com.herreratreib.gcp.covid.delta.transforms;

import com.google.api.client.util.Lists;
import com.herreratreib.gcp.covid.delta.model.CovidData;
import com.herreratreib.gcp.covid.delta.model.CovidDeltaPerState;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculateCovidDeltaPerState extends DoFn<KV<String, Iterable<CovidData>>, CovidDeltaPerState> {
    @ProcessElement
    public void processElement(final ProcessContext context) {
        final KV<String, Iterable<CovidData>> inputPair = context.element();
        final String state = inputPair.getKey();
        final Iterable<CovidData> covidDataIterable = inputPair.getValue();

        // 1. [Sort covid data points by date]
        final List<CovidData> covidDataList = Lists.newArrayList(covidDataIterable);
        covidDataList.sort(Comparator.comparing(CovidData::getDate));

        // 2. [Calculate weekly deltas]
        final Map<Integer, Long> confirmedCasesDeltaMap = new HashMap<>();
        final Map<Integer, Long> deathsDeltaMap = new HashMap<>();

        int week = 0;
        long previousCases = 0;
        long previousDeaths = 0;
        for (int i = 0; i <= covidDataList.size(); i += 7) {
            CovidData covidData = covidDataList.get(i);

            //confirmed cases
            Long currentCases = covidData.getConfirmedCases();
            confirmedCasesDeltaMap.put(week,
                                       currentCases - previousCases);
            previousCases = currentCases;

            //deaths
            Long currentDeaths = covidData.getDeaths();
            deathsDeltaMap.put(week,
                               currentDeaths - previousDeaths);
            previousDeaths = currentDeaths;

            week++;
        }

        // 3. [Populate and output object]
        final CovidDeltaPerState covidDeltaPerState = new CovidDeltaPerState();
        covidDeltaPerState.setState(state);
        covidDeltaPerState.setInitialDate(covidDataList.get(0)
                                                       .getDate());
        covidDeltaPerState.setInitialDate(covidDataList.get(covidDataList.size() - 1)
                                                       .getDate());
        covidDeltaPerState.setConfirmedCasesDeltaPerWeek(confirmedCasesDeltaMap);
        covidDeltaPerState.setDeathsDeltaPerWeek(deathsDeltaMap);

        context.output(covidDeltaPerState);
    }
}
