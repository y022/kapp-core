package com.kapp.kappcore.model.log;

import java.util.Properties;

public interface LogVal {
    Properties val();
    void log(Object o);
    void logError(Object o,Throwable e);
}
