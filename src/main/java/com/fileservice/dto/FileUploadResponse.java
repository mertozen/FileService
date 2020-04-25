package com.fileservice.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class FileUploadResponse {

    private UUID fileId;

    private String thumbnailname;

    private String extension;

    private String filename;
}
