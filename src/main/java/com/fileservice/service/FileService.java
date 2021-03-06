package com.fileservice.service;

import com.fileservice.dto.FileResponseDto;
import com.fileservice.dto.FileUploadResponse;
import com.fileservice.entity.File;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.util.UUID;

public interface FileService {

    public Mono<InputStream> load(String id);

    public Mono<InputStream> loadThumbnail(String id);

    public Flux<FileResponseDto> findAll();

    public Mono<FileUploadResponse> saveFile(FilePart filePart);

    public Mono<File> getFile(UUID id);
}
