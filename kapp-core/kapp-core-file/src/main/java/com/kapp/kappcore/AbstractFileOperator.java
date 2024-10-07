package com.kapp.kappcore;

import com.kapp.kappcore.entity.FileDI;
import com.kapp.kappcore.entity.FileSI;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Author:Heping
 * Date: 2024/9/27 19:18
 */
public abstract class AbstractFileOperator {
    public abstract FileSI upload(InputStream inputStream, String fileName) throws IOException;
    public abstract FileSI upload(InputStream inputStream, String fileName, String extra) throws IOException;
    public abstract FileDI download(FileSI fileInfo) throws IOException;

    protected String getFileId() {
        return UUID.randomUUID().toString();
    }
}
