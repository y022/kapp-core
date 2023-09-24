package com.kapp.kappcore.web.vo.update;

import lombok.Data;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Data
public class SaveRequestVo {

    @NonNull
    private String title;

    @NonNull
    private String body;

    @Nullable
    private String tag;


}
