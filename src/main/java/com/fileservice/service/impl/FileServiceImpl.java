package com.fileservice.service.impl;

import com.fileservice.entity.Content;
import com.fileservice.repository.FileRepository;
import com.fileservice.service.FileService;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    private Path base = Paths.get("/");

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public String save(FilePart file) throws IOException {
        Path tempFile = Files.createTempFile("test", UUID.randomUUID().toString());
        Content content = new Content();
        content.setDescription("ssdd");
        content.setExpiryDate(new Date());
        content.setContentType("sdsd");

        AsynchronousFileChannel channel =
                AsynchronousFileChannel.open(tempFile, StandardOpenOption.WRITE);
        DataBufferUtils.write(file.content(), channel, 0)
                .doOnComplete(() -> {
                    Mono<Content> savedContent= fileRepository.save(content);
                    savedContent.subscribe();
                })
                .subscribe();
        return file.filename();
    }

    public Mono<InputStream> load(String id) {

        return Mono.defer(() -> {
            try {
                return Mono.just(Files.newInputStream(path(id)));
            } catch (IOException e) {
                return Mono.error(e);
            }
        });
    }

    private Path path(String id) {
         base.resolve("ssdsd");
        return base.resolve(id);
    }
}
