package com.kapp.kappcore.web.api;

import com.kapp.kappcore.entity.FileUploadInfo;
import com.kapp.kappcore.fastdfs.FastDfsClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileApi {
    private final FastDfsClient fastDfsClient;

    @PostMapping("/upload")
    public FileUploadInfo uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            return fastDfsClient.upload(inputStream, file.getOriginalFilename());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
