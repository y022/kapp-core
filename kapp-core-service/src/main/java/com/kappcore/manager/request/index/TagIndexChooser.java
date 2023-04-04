package com.kappcore.manager.request.index;

import com.kappcore.manager.index.TagIndex;
import org.elasticsearch.action.index.IndexRequest;
import org.springframework.stereotype.Component;

@Component
public class TagIndexChooser implements IndexChoose {


    @Override
    public IndexRequest index(String tag) {
        IndexRequest indexRequest = new IndexRequest();
        if (tag != null) {
            String index = TagIndex.getIndexByTag(tag);
            if (index != null) {
                indexRequest.index(index);
            }
        }

        return indexRequest;

    }
}
