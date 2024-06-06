package com.kapp.kappcore.web.api.biz.share;

import com.kapp.kappcore.model.dto.share.ws.WSDTO;
import com.kapp.kappcore.model.dto.share.ws.WSQueryDTO;
import com.kapp.kappcore.service.biz.share.curd.ws.WSService;
import com.kapp.kappcore.web.vo.share.order.ws.WSQueryRequestVo;
import com.kapp.kappcore.web.vo.share.order.ws.WSQueryResponseVo;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/share/ws")
@RequiredArgsConstructor
public class WsApi {
    private final WSService wsService;
    private final MapperFacade mapperFacade;

    @PostMapping("/queryWS")
    WSQueryResponseVo queryWs(@RequestBody WSQueryRequestVo request) {
        return mapperFacade.map(wsService.batch(mapperFacade.map(request, WSQueryDTO.class)),WSQueryResponseVo.class);}
    @PostMapping("/save")
    void save(@RequestBody WSDTO request) {wsService.save(request);}
    @GetMapping("/delete")
    void delete(@RequestParam(value = "wsId", required = false) String wsId, @RequestParam(value = "wsCode", required = false) String wsCode) {wsService.delete(wsId, wsCode);}
    @PostMapping("/update")
    void update(@RequestBody WSDTO request){
        wsService.update(request);
    }
}
