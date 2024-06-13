package com.kapp.kappcore.model.dto.share.message;

import java.io.Serializable;

public interface KappMessage extends Serializable {
    String messageId();
    MessageType messageType();
}
