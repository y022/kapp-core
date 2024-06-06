package com.kapp.kappcore.service.domain.repository.share.ws;

import com.kapp.kappcore.model.dto.share.WSOrderDTO;
import com.kapp.kappcore.model.dto.share.WSOrderQueryDTO;
import com.kapp.kappcore.model.dto.share.WSOrderQueryResult;
import com.kapp.kappcore.model.entity.share.QWSOrder;
import com.kapp.kappcore.model.entity.share.WSOrder;
import com.kapp.kappcore.model.page.PageAndSort;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class WSOrderQuerySupporter {
    private final JPAQueryFactory queryFactory;
    private final MapperFacade mapperFacade;

    /**
     * support ws order query
     *
     * @param query query condition
     * @return query result
     */
    public WSOrderQueryResult batchQueryOrder(WSOrderQueryDTO query) {
        QWSOrder wSOrder = QWSOrder.wSOrder;
        JPAQuery<WSOrder> jpaQuery = queryFactory.selectFrom(wSOrder);
        BooleanBuilder where = new BooleanBuilder();
        if (StringUtils.isNotBlank(query.getWsId())) {
            where.and(wSOrder.orderId.eq(query.getWsId()));
        }
        if (StringUtils.isNotBlank(query.getName())) {
            where.and(wSOrder.name.likeIgnoreCase(query.getName()));
        }
        if (StringUtils.isNotBlank(query.getUserId())) {
            where.and(wSOrder.userId.eq(query.getUserId()));
        }
        if (StringUtils.isNotBlank(query.getStatus())) {
            where.and(wSOrder.status.eq(query.getStatus()));
        }
        jpaQuery.where(where);
        PageAndSort pageAndSort = query.getPageAndSort();
        jpaQuery.offset((long) (pageAndSort.getPageNum() - 1) * pageAndSort.getPageSize()).limit(pageAndSort.getPageSize());
        QueryResults<WSOrder> results = jpaQuery.fetchResults();
        return new WSOrderQueryResult(mapperFacade.mapAsList(results.getResults(), WSOrderDTO.class), pageAndSort.wireTotal(results.getTotal()));
    }

    public WSOrder queryByCode(String orderCode) {
        Objects.requireNonNull(orderCode);
        QWSOrder wSOrder = QWSOrder.wSOrder;
        JPAQuery<WSOrder> jpaQuery = queryFactory.selectFrom(wSOrder);
        List<WSOrder> result = jpaQuery.where(new BooleanBuilder().and(wSOrder.orderCode.eq(orderCode))).fetch();
        if (result == null || result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

}
