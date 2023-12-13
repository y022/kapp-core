package com.kapp.kappcore.web.api.base;

import com.kapp.kappcore.base.auth.AuthContext;
import com.kapp.kappcore.base.auth.AuthManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/base")
@RequiredArgsConstructor
public class KappAuthApi {
    private final AuthManager authManager;

    @PostMapping("/auth")
    void auth() {
        if (!authManager.auth(AuthContext.staticBaseUser())) {
            throw new RuntimeException("认证失败！");
        }
    }
}
