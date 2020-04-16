package com.fileservice.service;

import com.fileservice.entity.File;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.InputStream;

public interface FileService {

    public Mono<InputStream> load(String id);

    public Flux<File> findAll();

    Mono<File> saveFile(FilePart filePart);
}
