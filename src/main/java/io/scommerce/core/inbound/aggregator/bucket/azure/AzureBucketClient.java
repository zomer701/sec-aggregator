package io.scommerce.core.inbound.aggregator.bucket.azure;

import com.azure.core.credential.AzureNamedKeyCredential;
import com.azure.storage.blob.BlobAsyncClient;
import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.models.ParallelTransferOptions;
import io.scommerce.core.inbound.aggregator.bucket.BucketClient;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
//@ConditionalOnProperty(value = "azure.blobstorage.service.enabled", havingValue = "true", matchIfMissing = true)
public class AzureBucketClient implements BucketClient {

    private long blockSize = 1024*18;
    private int maxConcurrency = 10;

    @Override
    public Mono<Void> upload(FilePart file) {
        ParallelTransferOptions parallelTransferOptions = new ParallelTransferOptions()
                .setBlockSizeLong(blockSize)
                .setMaxConcurrency(maxConcurrency);
//
//        Flux<ByteBuffer> byteBuffers = file
//                .content()
//                .flatMapSequential(dataBuffer -> Flux.fromIterable(dataBuffer::readableByteBuffers));

        return getClient(file.filename()).upload(file.content().map(DataBuffer::asByteBuffer),
                        parallelTransferOptions).doOnNext(response ->
                log.info(response.getETag()))
                .then();
    }


    private BlobAsyncClient getClient(String name) {

        var connectionString = "DefaultEndpointsProtocol=https;AccountName=8v9k7h5sxr4wnyk68id441l;" +
                "   AccountKey=QL2Qq5p9Ok4t15QByWvL/jvv2hcC2gdQ0LLmtoTaXPGX8RykH3vN5QniPqT1SXIayosBlUWHDLmEUns4tztk8g==;" +
                "   EndpointSuffix=core.windows.net";

        return new BlobClientBuilder()
                .blobName(name)
                .containerName("hybris")
                .connectionString(connectionString)
                .buildAsyncClient();
    }
}
