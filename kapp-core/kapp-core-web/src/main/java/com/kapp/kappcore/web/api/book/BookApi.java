package com.kapp.kappcore.web.api.book;

import com.kapp.kappcore.model.dto.book.BookMeta;
import com.kapp.kappcore.task.book.KappBookSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
