package com.fileservice.controller;

import com.fileservice.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/file")
public class FileController {



    Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileService fileService;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    public Mono<String> fileUpload(@RequestPart("file") FilePart filePart) throws IOException {

        return Mono.just(fileService.save(filePart));
    }

    @GetMapping("{id}")
    public  Mono<ResponseEntity<InputStreamResource>> download(@PathVariable String id){

        return fileService.load(id).map(x -> ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,String.format("attachment;filename={}",id)).body(new InputStreamResource(x)));
    }


}
