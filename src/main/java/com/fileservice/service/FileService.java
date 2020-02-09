package com.fileservice.service;

import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    public String save(FilePart file) throws IOException;

    public Mono<InputStream> load(String id);


}
