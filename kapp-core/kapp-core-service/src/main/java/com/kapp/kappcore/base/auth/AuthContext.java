package com.kapp.kappcore.base.auth;

import com.kapp.kappcore.domain.KappCons;
import lombok.Data;

public interface AuthContext {

    boolean baseUser();

    String userId();

    String userName();

    String password();

     static AuthContext staticBaseUser() {
        return new AuthContext() {
            @Override
            public String userId() {
                return "adminId";
            }

            @Override
            public String userName() {
                return KappCons.baseUser;
            }

            @Override
            public String password() {
                return KappCons.basePassword;
            }

            @Override
            public boolean baseUser() {
                return true;
            }
        };
    }
}
