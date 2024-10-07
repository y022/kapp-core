package com.kapp.kappcore.fastdfs;

import com.kapp.kappcore.AbstractFileOperator;
import com.kapp.kappcore.entity.FileDI;
import com.kapp.kappcore.entity.FileSystem;
import com.kapp.kappcore.entity.FileSI;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.StorageClient;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Author:Heping
 * Date: 2024/9/27 19:02
 */
public class FastDfsClient extends AbstractFileOperator {
    private final StorageClient storageClient;

    public FastDfsClient(StorageClient storageClient) {
        this.storageClient = storageClient;
    }

    @Override
    public FileSI upload(InputStream inputStream, String fileName) throws IOException {
        String extraContent = StringUtils.substringAfter(fileName, ".");
        return upload(inputStream, fileName, extraContent);
    }

    @Override
    public FileSI upload(InputStream inputStream, String fileName, String extra) throws IOException {
        if (StringUtils.isEmpty(fileName)) {
            throw new RuntimeException("fileName is empty");
        }
        if (StringUtils.isEmpty(extra)) {
            throw new RuntimeException("extra is empty");
        }
        try {
            byte[] allBytes = FileCopyUtils.copyToByteArray(inputStream);
            String[] uploadRes = storageClient.upload_file(allBytes, extra, null);
            FileSI fileSI = new FileSI();
            fileSI.setFileId(getFileId());
            fileSI.setFileSize(String.valueOf(allBytes.length / 1024));
            fileSI.setFileName(fileName);
            fileSI.setFileStorePath(uploadRes[1]);
            fileSI.setFileSystem(FileSystem.FAST_DFS.getSystemCode());
            return fileSI;
        } catch (MyException e) {
            throw new IOException(e);
        }
    }

    @Override
    public FileDI download(FileSI fileInf) throws IOException {

        return null;
    }
}
