package io.scommerce.core.inbound.aggregator.controller;


import io.scommerce.core.inbound.aggregator.service.CommandProducerService;
import io.scommerce.core.inbound.aggregator.service.FileStorageService;
import io.scommerce.core.inbound.shared.dto.CommandDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import static io.scommerce.core.inbound.shared.constants.Constants.ProcessorChannels;
@RestController
@RequestMapping("/v1/{channel}/")
@AllArgsConstructor
public class FileController {

    private final FileStorageService storageService;
    private final CommandProducerService commandProducerService;

    @PostMapping("/upload")
    public Mono<ResponseEntity<Void>> uploadFile(@RequestPart("file") Mono<FilePart> filePartMono,
                                                 @PathVariable("channel") ProcessorChannels channel) {
        //TODO channel validation
        return storageService.save(filePartMono, channel)
                .flatMap(file -> commandProducerService.send(CommandDTO.builder()
                        .pk(System.currentTimeMillis())
                        .name(file)
                        .user("test1@test")
                        .channel(channel.name())
                        .build()))
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
