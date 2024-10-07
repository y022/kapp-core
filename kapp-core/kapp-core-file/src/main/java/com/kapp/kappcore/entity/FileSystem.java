package com.kapp.kappcore.entity;

import lombok.Getter;

@Getter
public enum FileSystem {
    FAST_DFS("FAST_DFS"),
    MINIO("MINIO");
    private final String systemCode;

    FileSystem(String systemCode) {
        this.systemCode = systemCode;
    }
}
