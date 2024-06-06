package com.kapp.kappcore.model.dto.share.pay;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayStatus {

    UN_PAY("01"), PAY_SUCCESS("02"), GIVE_UP_PAY("03"), TIME_OUT("04");


    private final String code;
}
