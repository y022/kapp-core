package com.kapp.kappcore.service.domain.repository.share;

import com.kapp.kappcore.model.page.PageAndSort;
import com.querydsl.jpa.impl.JPAQuery;

public class JPAQueryTool {

    public static <T> void page_sort(JPAQuery<T> query, PageAndSort pageAndSort) {
        if (pageAndSort != null) {
            query.offset((long) (pageAndSort.getPageNum() - 1) * pageAndSort.getPageSize()).limit(pageAndSort.getPageSize());
//            if (StringUtils.isNotBlank(pageAndSort.getSort())) {
//                new OrderSpecifier(pageAndSort.isAsc() ? Order.ASC : Order.DESC)
//                query.orderBy()
//            }
        }
    }
}
