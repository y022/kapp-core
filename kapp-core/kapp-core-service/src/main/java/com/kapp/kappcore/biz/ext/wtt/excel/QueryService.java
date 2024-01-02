package com.kapp.kappcore.biz.ext.wtt.excel;

import com.kapp.kappcore.domain.repository.ControlValveRepository;
import com.kapp.kappcore.wtt.ControlValve;
import com.kapp.kappcore.wtt.QControlValve;
import com.kapp.kappcore.wtt.query.ControlValveQueryReq;
import com.kapp.kappcore.wtt.query.ControlValveQueryResp;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueryService {
    private final ControlValveRepository controlValveRepository;
    private final JPAQueryFactory queryFactory;

    public ControlValveQueryResp query(ControlValveQueryReq query) {
//        query = new ControlValveQueryReq();
        QControlValve qc = QControlValve.controlValve;

        QueryResults<ControlValve> controlValveQueryResults = queryFactory.selectFrom(qc).orderBy(qc.controlValveNo.asc()).offset((long) (query.getIndex() - 1) * query.getSize()).limit(query.getSize()).fetchResults();
        ControlValveQueryResp controlValveQueryResp = new ControlValveQueryResp();
        controlValveQueryResp.setData(controlValveQueryResults.getResults());
        controlValveQueryResp.setTotal(controlValveQueryResults.getTotal());
        return controlValveQueryResp;
    }
}
