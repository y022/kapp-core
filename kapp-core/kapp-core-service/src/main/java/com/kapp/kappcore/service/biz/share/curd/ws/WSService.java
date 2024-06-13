package com.kapp.kappcore.service.biz.share.curd.ws;

import com.kapp.kappcore.model.constant.ExCode;
import com.kapp.kappcore.model.dto.share.ws.WSDTO;
import com.kapp.kappcore.model.dto.share.ws.WSQueryDTO;
import com.kapp.kappcore.model.dto.share.ws.WSQueryResult;
import com.kapp.kappcore.model.entity.share.WS;
import com.kapp.kappcore.model.exception.BizException;
import com.kapp.kappcore.service.domain.repository.share.order.WsQuerySupporter;
import com.kapp.kappcore.service.domain.repository.share.ws.WSRepository;
import com.kapp.kappcore.support.tool.date.DateTool;
import com.kapp.kappcore.support.tool.id.IdTool;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class WSService {

    private final WsQuerySupporter querySupporter;
    private final WSRepository wsRepository;
    private final MapperFacade mapperFacade;

    public WSQueryResult batch(WSQueryDTO queryRequest) {
        return querySupporter.batchQuery(queryRequest);
    }

    public void save(WSDTO request) {
        String id = IdTool.getId();
        String code = IdTool.getCode();
        String now = DateTool.now();
        WS ws = WS.builder().wsId(id).wsCode(code).name(request.getName()).description(request.getDescription()).unitPrice(request.getUnitPrice()).inventory(request.getInventory()).build();
        ws.updateTime(now, now);
        wsRepository.save(ws);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(String wsId, String wsCode) {
        if (StringUtils.isNotBlank(wsId)) {
            wsRepository.deleteById(wsId);
        } else if (StringUtils.isNotBlank(wsCode)) {
            wsRepository.deleteByWsCode(wsCode);
        } else
            throw new BizException(ExCode.error, "delete ket is not exist!");
    }

    public void update(WSDTO request) {
        if (StringUtils.isBlank(request.getWsId())) {
            throw new BizException(ExCode.error, "ws id is not exist!");
        }
        wsRepository.save(mapperFacade.map(request, WS.class));
    }
}

