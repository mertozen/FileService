package com.fileservice.util;

import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {

    private final static Path base = Paths.get("/Users/mert/Documents/Projects/FileService/");

    public static void saveToDisk(FilePart filePart, String fileId) {

        File savedFileToDisk = new File(base.toString() + "/" + fileId);
        filePart.transferTo(savedFileToDisk);
        ThumbnailCreatorUtil.createThumbnail(savedFileToDisk,fileId);
    }

    public static Mono<InputStream> loadFile(String fileId) {

        return Mono.defer(() -> {
            try {
                return Mono.just(Files.newInputStream(path(fileId)));
            } catch (IOException e) {
                return Mono.error(e);
            }
        });
    }


    public static Mono<InputStream> loadThumbnailFile(String fileId) {
        String thumbnail = fileId+"_thumbnail";
        return Mono.defer(() -> {
            try {
                return Mono.just(Files.newInputStream(path(thumbnail)));
            } catch (IOException e) {
                return Mono.error(e);
            }
        });
    }

    private static Path path(String fileId) {

        return base.resolve(fileId);
    }
}
