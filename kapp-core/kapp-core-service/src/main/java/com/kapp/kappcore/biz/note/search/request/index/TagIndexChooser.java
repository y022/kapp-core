package com.kapp.kappcore.biz.note.search.request.index;

import com.kapp.kappcore.biz.note.search.index.TagIndex;
import org.elasticsearch.action.index.IndexRequest;
import org.springframework.stereotype.Component;

@Component
public class TagIndexChooser implements IndexChoose {


    @Override
    public IndexRequest index(String tag) {
        IndexRequest indexRequest = new IndexRequest();
        if (tag != null) {
            String index = TagIndex.getIndex(tag);
            if (index != null) {
                indexRequest.index(index);
            }
        }

        return indexRequest;

    }
}
