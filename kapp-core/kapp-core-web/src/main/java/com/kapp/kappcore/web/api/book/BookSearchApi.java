package com.kapp.kappcore.web.api.book;

import com.kapp.kappcore.search.common.ExtSearchRequest;
import com.kapp.kappcore.search.common.SearchResult;
import com.kapp.kappcore.search.endpoint.KappDocSearcher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book/search")
@RequiredArgsConstructor
public class BookSearchApi {

    private final KappDocSearcher kappDocSearcher;

    @GetMapping("/normal")
    public SearchResult<?> search(@RequestBody ExtSearchRequest extSearchRequest) {
        return kappDocSearcher.search(extSearchRequest);
    }

    @GetMapping("/scroll")
    public SearchResult<?> scroll(@RequestBody ExtSearchRequest extSearchRequest) {
        return kappDocSearcher.search(extSearchRequest);
    }


}
