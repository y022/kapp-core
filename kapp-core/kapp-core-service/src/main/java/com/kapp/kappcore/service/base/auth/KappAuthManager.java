package com.kapp.kappcore.service.base.auth;

import com.kapp.kappcore.service.domain.KappCons;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KappAuthManager implements AuthManager {


    @Override
    public boolean auth(AuthContext authContext) {
        if (authContext.baseUser()) {
            return KappCons.baseUser.equals(authContext.userName()) && KappCons.basePassword.equals(authContext.password());
        }
        return false;
    }

    @Override
    public void register(AuthContext context) {

    }
}
