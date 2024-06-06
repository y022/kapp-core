package com.kapp.kappcore.model.dto.share.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeMessage implements Message {
    private String id;
    private TimeUnit unit;
    private int EffectiveTime;

    @Override
    public String messageId() {
        return getId();
    }


}
