package com.kapp.kappcore.service.base.auth;


public interface AuthManager {
    boolean auth(AuthContext authContext);

    default void register(AuthContext context) {
        throw new UnsupportedOperationException("暂不支持注册!");
    }
}
