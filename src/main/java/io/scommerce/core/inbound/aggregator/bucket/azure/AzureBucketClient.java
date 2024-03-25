package io.scommerce.core.inbound.aggregator.bucket.azure;

import com.azure.storage.blob.BlobAsyncClient;
import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.models.ParallelTransferOptions;
import io.scommerce.core.inbound.aggregator.bucket.BucketClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;

@Slf4j
@Service
@RequiredArgsConstructor
public class AzureBucketClient implements BucketClient {

    long blockSize = 2 * 1024 * 1024;
    private int maxConcurrency = 4;

    @Override
    public Mono<Void> upload(FilePart file) {
        ParallelTransferOptions parallelTransferOptions = new ParallelTransferOptions()
                .setBlockSizeLong(blockSize).setMaxConcurrency(maxConcurrency)
                .setProgressListener(bytesTransferred -> System.out.println("uploaded:" + bytesTransferred));

        Flux<ByteBuffer> byteBuffers = file
                .content()
                .flatMapSequential(dataBuffer -> Flux.fromIterable(dataBuffer::readableByteBuffers));
        return getClient(file.filename()).upload(byteBuffers,
                        parallelTransferOptions, true).doOnNext(response ->
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
