package com.fileservice.service.impl;

import com.fileservice.dto.FileResponseDto;
import com.fileservice.dto.FileUploadResponse;
import com.fileservice.entity.File;
import com.fileservice.repository.FileRepository;
import com.fileservice.service.FileService;
import com.fileservice.util.FileUtil;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;


    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public Mono<InputStream> load(String id) {

        return FileUtil.loadFile(id);
    }

    public Mono<InputStream> loadThumbnail(String id) {

        return FileUtil.loadThumbnailFile(id);
    }

    @Override
    public Flux<FileResponseDto> findAll() {
        //TODO Mapper implementation
        return fileRepository.findAll().map(file -> {

            FileResponseDto fileResponseDto = new FileResponseDto();
            fileResponseDto.setFileId(file.getId());
            fileResponseDto.setDescription(file.getDescription());
            fileResponseDto.setThumbnailname(file.getThumbnail());
            fileResponseDto.setExpiryDate(file.getExpiryDate());
            fileResponseDto.setExtension(file.getExt());
            fileResponseDto.setFilename(file.getFilename());

            return fileResponseDto;
        });
    }

    @Override
    public Mono<FileUploadResponse> saveFile(FilePart filePart) {

        UUID fileId = UUID.randomUUID();
        FileUtil.saveToDisk(filePart, fileId.toString());
        File file = new File();
        file.setId(fileId);
        file.setFilename(filePart.filename());
        file.setExt(filePart.headers().getContentType().getType());
        file.setExpiryDate(new Date());
        fileRepository.save(file).subscribe();

        FileUploadResponse fileUploadResponse = new FileUploadResponse();
        fileUploadResponse.setExtension(filePart.headers().getContentType().getType());
        fileUploadResponse.setFileId(fileId);
        fileUploadResponse.setFilename(filePart.filename());
        fileUploadResponse.setThumbnailname(fileId + "_thumbnail");
        return Mono.just(fileUploadResponse);
    }


    public Mono<File> getFile(UUID id) {

        return fileRepository.findById(id);
    }
}
