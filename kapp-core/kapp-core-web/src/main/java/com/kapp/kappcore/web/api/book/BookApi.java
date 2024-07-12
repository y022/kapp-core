package com.kapp.kappcore.web.api.book;

import com.kapp.kappcore.model.dto.book.BookMeta;
import com.kapp.kappcore.task.book.KappBookSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Author:Heping
 * Date: 2024/7/9 18:21
 */

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookApi {
    private final KappBookSaveService kappBookSaveService;

    @PostMapping("/asyncSave")
    void save(@RequestBody List<BookMeta> bookMeta) {
        for (BookMeta meta : bookMeta) {
            kappBookSaveService.save(meta.getPath(), meta);
        }
    }

    @GetMapping("/asyncSave/list")
    void save_list(@RequestParam("path") String path) {

        try {
            Files.list(Paths.get(path)).forEach(p -> {
                Path fileName = p.getFileName();
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
