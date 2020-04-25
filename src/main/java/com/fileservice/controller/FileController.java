package com.fileservice.controller;

import com.fileservice.entity.File;
import com.fileservice.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/files")
public class FileController {


    Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    public Mono<File> fileUpload(@RequestPart("file") FilePart filePart) throws IOException {

        return fileService.saveFile(filePart);
    }

    @GetMapping
    public Flux<File> getFiles(){

        return fileService.findAll();
    }

    @GetMapping("{id}")
    public Mono<File> getFile(@PathVariable String id){

        return fileService.getFile(UUID.fromString(id));
    }

    @GetMapping("/download/{id}")
    public  Mono<ResponseEntity<InputStreamResource>> download(@PathVariable String id){

        return fileService.load(id).map(x -> ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,String.format("attachment;filename={}",id)).body(new InputStreamResource(x)));
    }
}
