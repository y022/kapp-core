package com.kapp.kappcore.service.domain.repository.share.ws;

import com.kapp.kappcore.model.entity.share.WS;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WSRepository extends JpaRepository<WS, String> {
    void deleteByWsCode(String wsCode);

}
