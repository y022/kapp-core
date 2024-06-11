package com.kapp.kappcore.model.dto.share.message.impl;

import com.kapp.kappcore.model.dto.share.message.KappMessage;
import com.kapp.kappcore.model.dto.share.message.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeKappMessage implements KappMessage {
    private String id;
    private Duration duration;
    private MessageType messageType;

    @Override
    public String messageId() {
        return getId();
    }

    @Override
    public MessageType messageType() {
        return messageType;
    }


}
