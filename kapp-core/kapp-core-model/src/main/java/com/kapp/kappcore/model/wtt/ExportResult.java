package com.kapp.kappcore.model.wtt;

import lombok.Data;

import java.io.ByteArrayOutputStream;

@Data
public class ExportResult {
    private String fileName;
    private ByteArrayOutputStream byteArrayOutputStream;
}
