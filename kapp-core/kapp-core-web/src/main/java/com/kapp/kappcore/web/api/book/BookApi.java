package com.kapp.kappcore.web.api.book;

import cn.hutool.core.util.ZipUtil;
import com.kapp.kappcore.model.dto.book.BookMeta;
import com.kapp.kappcore.task.book.KappBookSaveService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Author:Heping
 * Date: 2024/7/9 18:21
 */

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookApi {
    private final KappBookSaveService kappBookSaveService;
    private static final String[] EXTENSIONS = {"zip"};
    private static final String[] TXT_EXTENSIONS = {"txt"};

    @PostMapping("/asyncSave")
    void save(@RequestBody List<BookMeta> bookMeta) {
        for (BookMeta meta : bookMeta) {
            kappBookSaveService.save(meta.getPath(), meta);
        }
    }

    @GetMapping("/asyncSave/list")
    void save_list(@RequestParam("path") String path) {
        try {
            Collection<File> files = FileUtils.listFiles(new File(path), TXT_EXTENSIONS, true);
            for (File file : files) {
                BookMeta bookMeta = new BookMeta();
                bookMeta.setAuthor("未知");
                bookMeta.setPath(file.getAbsolutePath());
                bookMeta.setTitle(StringUtils.substringBeforeLast(file.getName(), "."));
                bookMeta.setTag("网文");
                kappBookSaveService.save(bookMeta.getPath(), bookMeta);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/asyncSave/list_unzip")
    void save_list_unzip(@RequestParam("path") String path, @RequestParam("fileDir") String fileDir) {
        try {
            Collection<File> files = FileUtils.listFiles(new File(path), EXTENSIONS, true);
            for (File file : files) {
                File unzip = ZipUtil.unzip(file.getAbsolutePath(), fileDir + File.separator + "unzip" + File.separator + file.getName());
                for (File listFile : Objects.requireNonNull(unzip.listFiles())) {
                    BookMeta bookMeta = new BookMeta();
                    bookMeta.setAuthor("未知");
                    bookMeta.setPath(listFile.getAbsolutePath());
                    bookMeta.setTitle(StringUtils.substringBeforeLast(listFile.getName(), "."));
                    bookMeta.setTag("网文");
                    kappBookSaveService.save(bookMeta.getPath(), bookMeta);
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
