package com.kapp.kappcore;

import com.kapp.kappcore.entity.FileDownloadInfo;
import com.kapp.kappcore.entity.FileUploadInfo;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Author:Heping
 * Date: 2024/9/27 19:18
 */
public abstract class AbstractFileOperator {
    public abstract FileUploadInfo upload(InputStream inputStream, String fileName) throws IOException;

    public abstract FileUploadInfo upload(InputStream inputStream, String fileName, String extra);

    public abstract FileDownloadInfo download(FileUploadInfo fileInf) throws IOException;

    protected String getFileId() {
        return UUID.randomUUID().toString();
    }
}
