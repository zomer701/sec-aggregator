package io.scommerce.core.inbound.aggregator.clients.biqquery;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.Job;
import com.google.cloud.bigquery.JobId;
import com.google.cloud.bigquery.JobInfo;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;
import com.google.cloud.http.HttpTransportOptions;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
@RequiredArgsConstructor
public class BigQueryClient {

    @Value("${biqquery.auth.file}")
    private String biqqueryAuthFile;

    public TableResult queryPagination(String query, String token, int size) throws IOException, InterruptedException {

        QueryJobConfiguration queryJobConfiguration = QueryJobConfiguration
                .newBuilder(query)
                .build();

        JobId jobId = JobId.of(UUID.randomUUID().toString());
        log.info("page request  start " + jobId);
        Job queryJob = getBigQuery().create(
                JobInfo.newBuilder(queryJobConfiguration)
                        .setJobId(jobId)
                        .build()
        );

        queryJob = queryJob.waitFor();

        if (queryJob == null) {
            throw new IllegalStateException("Job no longer exists");
        } else if (queryJob.getStatus().getError() != null) {
            throw new IllegalStateException(queryJob.getStatus().getError().toString());
        }

        BigQuery.QueryResultsOption option = StringUtils.isNotEmpty(token) ?
                BigQuery.QueryResultsOption.pageToken(token).pageSize(size) :
                BigQuery.QueryResultsOption.pageSize(size);


        TableResult result = queryJob.getQueryResults(option);

        log.info("page request  end " + jobId);

        return result;
    }

    private BigQuery getBigQuery() throws IOException {
        var privateKeyString = Files.readString(Path.of(biqqueryAuthFile), StandardCharsets.UTF_8);
        HttpTransportOptions transportOptions = BigQueryOptions.getDefaultHttpTransportOptions();
        transportOptions = transportOptions.toBuilder().setConnectTimeout(60000).setReadTimeout(60000)
                .build();

        return BigQueryOptions.newBuilder()
                .setTransportOptions(transportOptions)
                .setCredentials(ServiceAccountCredentials
                        .fromStream(new ByteArrayInputStream(privateKeyString.getBytes())).toBuilder().build())
                .build().getService();
    }
}
