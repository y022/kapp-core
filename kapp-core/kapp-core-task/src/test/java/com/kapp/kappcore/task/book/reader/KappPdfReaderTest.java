package com.kapp.kappcore.task.book.reader;

import com.kapp.kappcore.model.dto.book.BookMeta;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Author:Heping
 * Date: 2024/7/10 17:15
 */
class KappPdfReaderTest {

    @Test
    void read() {
        KappPdfReader kappPdfReader = new KappPdfReader();
        Path path = Paths.get("C:\\doucment\\Book\\计算机\\代码大全(第2版) ((美)迈克康奈尔(McConnell.S)著 金戈, 汤凌, 陈硕, 张菲译) (Z-Library).pdf");
        kappPdfReader.read(path, new BookMeta(), System.out::println);


    }
}