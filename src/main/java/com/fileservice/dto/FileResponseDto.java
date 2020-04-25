package com.fileservice.dto;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class FileResponseDto {

    private UUID fileId;

    private String description;

    private String thumbnailname;

    private String extension;

    private String filename;

    private Date expiryDate;
}
