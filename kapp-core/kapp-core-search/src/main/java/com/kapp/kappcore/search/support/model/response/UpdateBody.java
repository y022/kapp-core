package com.kapp.kappcore.search.support.model.response;

import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Author:Heping
 * Date: 2024/7/26 17:21
 */
@Data
public class UpdateBody extends AbstractBody {

    private Set<String> successIds;
    private Set<String> failureIds;

    public UpdateBody() {
        this.successIds = new HashSet<>(6);
        this.failureIds = new HashSet<>(6);
    }

    public void wire(String id, boolean success) {
        if (success) {
            successIds.add(id);
        } else {
            failureIds.add(id);
        }
    }


    @Override
    public List<?> data() {
        return List.of();
    }
}
