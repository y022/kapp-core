package com.kapp.kappcore.domain.repository;

import com.kapp.kappcore.model.wtt.ControlValve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ControlValveRepository extends JpaRepository<ControlValve,String> {
    ControlValve findByControlValveNo(String no);
}
