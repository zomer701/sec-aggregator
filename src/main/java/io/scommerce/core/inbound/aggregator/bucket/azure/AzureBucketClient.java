package io.scommerce.core.inbound.aggregator.bucket.azure;

import com.azure.storage.blob.BlobAsyncClient;
import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.ProgressReceiver;
import com.azure.storage.blob.models.ParallelTransferOptions;
import io.scommerce.core.inbound.aggregator.bucket.BucketClient;
import io.scommerce.core.inbound.shared.constants.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;

import static io.scommerce.core.inbound.shared.util.UrlsUtil.azureBucketUrl;

@Slf4j
@Service
@RequiredArgsConstructor
public class AzureBucketClient implements BucketClient {

    long blockSize = 50 * 1024 * 1024;
    private int maxConcurrency = 8;

    @Override
    public Mono<Void> upload(FilePart file,  Constants.ProcessorChannels channel) {
        ParallelTransferOptions parallelTransferOptions = new ParallelTransferOptions()
                .setBlockSizeLong(blockSize).setMaxConcurrency(maxConcurrency)
                .setProgressListener(bytesTransferred -> log.info("uploaded:" + bytesTransferred))
                .setProgressReceiver(bytesTransferred -> log.info("uploaded:" + bytesTransferred));;

        Flux<ByteBuffer> byteBuffers = file
                .content()
                .flatMapSequential(dataBuffer -> Flux.fromIterable(dataBuffer::readableByteBuffers));
        return getClient(azureBucketUrl(channel.name(), file.filename())).upload(byteBuffers,
                        parallelTransferOptions, true).doOnNext(response -> {
                    log.info(response.getETag());
                    log.info("HERE");
                })
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
