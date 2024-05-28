package com.kapp.kappcore.model.dto.share;

public interface Order {
    void updateTime();
    void updateStatus(String status);
    String orderCode();

}
