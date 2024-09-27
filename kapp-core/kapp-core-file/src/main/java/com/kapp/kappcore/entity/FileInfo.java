package com.kapp.kappcore.entity;

import lombok.Data;

@Data
public class FileInfo {
    private String fileId;
    private String fileName;
    private String fileSize;
    private String fileSystem;
    private String fileStorePath;
}
