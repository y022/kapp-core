package com.kapp.kappcore.fastdfs;

import com.kapp.kappcore.AbstractFileOperator;
import com.kapp.kappcore.entity.FileDownloadInfo;
import com.kapp.kappcore.entity.FileUploadInfo;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.StorageClient;

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
    public FileUploadInfo upload(InputStream inputStream, String fileName) throws IOException {
        if (StringUtils.isEmpty(fileName)) {
            throw new RuntimeException("fileName is empty");
        }
        String extra = StringUtils.substringAfter(fileName, ".");
        try {
            String[] res = storageClient.upload_file(inputStream.readAllBytes(), extra, null);
            FileUploadInfo fileUploadInfo = new FileUploadInfo();
            String fileId = getFileId();
            fileUploadInfo.setFileId(fileId);
            fileUploadInfo.setFileName(fileName);
            fileUploadInfo.setFileStorePath(res[1]);
            fileUploadInfo.setFileSystem("fastdfs");
            return fileUploadInfo;
        } catch (MyException e) {
            throw new IOException(e);
        }
    }

    @Override
    public FileUploadInfo upload(InputStream inputStream, String fileName, String extra) {
        return null;
    }

    @Override
    public FileDownloadInfo download(FileUploadInfo fileInf) throws IOException {

        return null;
    }
}
