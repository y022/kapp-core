package com.kapp.kappcore.service.domain.repository.share.order;

import com.kapp.kappcore.model.dto.share.ws.WSDTO;
import com.kapp.kappcore.model.dto.share.ws.WSQueryDTO;
import com.kapp.kappcore.model.dto.share.ws.WSQueryResult;
import com.kapp.kappcore.model.entity.share.QWS;
import com.kapp.kappcore.model.entity.share.WS;
import com.kapp.kappcore.model.page.PageAndSort;
import com.kapp.kappcore.service.domain.repository.share.JPAQueryTool;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class WsQuerySupporter {
    private final JPAQueryFactory queryFactory;
    private final MapperFacade mapperFacade;

    public WSQueryResult batchQuery(WSQueryDTO queryRequest) {
        QWS ws = QWS.wS;
        JPAQuery<WS> query = queryFactory.selectFrom(ws);
        BooleanBuilder where = new BooleanBuilder();
        if (StringUtils.isNotBlank(queryRequest.getWsId())) {
            where.and(ws.wsId.eq(queryRequest.getWsId()));
        }
        if (StringUtils.isNotBlank(queryRequest.getName())) {
            where.and(ws.name.likeIgnoreCase(queryRequest.getName()));
        }
        query.where(where);
        PageAndSort pageAndSort = queryRequest.getPageAndSort();
        if (pageAndSort == null) {
            pageAndSort = PageAndSort.defaultOrder();
        }
        JPAQueryTool.page_sort(query, pageAndSort);
        QueryResults<WS> results = query.fetchResults();
        return new WSQueryResult(mapperFacade.mapAsList(results.getResults(), WSDTO.class), pageAndSort.wireTotal(results.getTotal()));
    }
}
