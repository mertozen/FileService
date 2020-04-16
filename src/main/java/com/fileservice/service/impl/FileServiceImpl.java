package com.fileservice.service.impl;

import com.fileservice.entity.File;
import com.fileservice.repository.FileRepository;
import com.fileservice.service.FileService;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    private Path base = Paths.get("/Users/mert/Documents/Projects/FileService/");

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
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

    @Override
    public Flux<File> findAll() {
        return fileRepository.findAll();
    }

    @Override
    public Mono<File> saveFile(FilePart filePart) {

        UUID id = UUID.randomUUID();
        java.io.File savedFileToDisk = new java.io.File(base.toString()+"/"+id);
        filePart.transferTo(savedFileToDisk);
        File file = new File();
        file.setId(id);
        file.setFilename(filePart.filename());
        file.setExt(filePart.headers().getContentDisposition().getType());
        file.setExpiryDate(null); //TODO expiryDate will be added
        fileRepository.save(file).subscribe();
        return Mono.just(file);
    }

    private Path path(String id) {

        return base.resolve(id);
    }
}
