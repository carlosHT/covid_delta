##Covid Delta
This pipeline reads Covid data from the [The New York Times US Coronavirus Database](https://console.cloud.google.com/marketplace/product/the-new-york-times/covid19_us_cases) and calculates the weekly rate of change in confirmed cases and deaths per state in the United States.

####Local Execution

```
./gradlew execute -DmainClass=com.herreratreib.gcp.covid.delta.CovidDeltaPipeline \
                  -Dexec.args="--runner=DirectRunner \
                               --project=localproject \
                               --optionsBundle=covid19_by_state" \
                  --debug-jvm
```

####Generating a Template for Deployment in DataFlow
1. Run the shadowJar gradle task.
2. On a Terminal window, go into "covid_delta/build/libs".
3. Make sure the "covid_delta-LOCAL-SNAPSHOT-all.jar" file is there.
4. Execute the following command:
   
    ```
   java -cp covid_delta-LOCAL-SNAPSHOT-all.jar com.herreratreib.gcp.covid.delta.CovidDeltaPipeline \
        --runner=DataFlowRunner \
        --project=gcp-com-herrera-treib \
        --region=us-central1 \
        --optionsBundle=covid19_by_state \
        --numWorkers=2 \
        --maxNumWorkers=10 \
        --workerMachineType=n1-standard-1 \
        --tempLocation=gs://gcp-com-herrera-treib/temp_files \
        --templateLocation=gs://gcp-com-herrera-treib/dataflow_templates/covid_delta/07_05_2020
   ```
   
   <sub><sup>
   Note: The "pipelineOptionBundle" defines a set of multiple options that are passed to the pipeline. Each option bundle is associated to a JSON file under the "deployment/pipeline_option_bundles" folder.
   Some options have to be explicitly passed on the command line. 
   </sup></sub>