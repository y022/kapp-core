package com.kapp.kappcore.web.api;

import com.kapp.kappcore.fastdfs.FastDfsClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileApi {
    private final FastDfsClient fastDfsClient;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {

        return "文件上传失败";
    }
}
