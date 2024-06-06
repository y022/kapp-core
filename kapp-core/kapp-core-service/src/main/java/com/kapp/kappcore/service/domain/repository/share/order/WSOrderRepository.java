package com.kapp.kappcore.service.domain.repository.share.order;

import com.kapp.kappcore.model.entity.share.WSOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WSOrderRepository extends JpaRepository<WSOrder, String> {
}