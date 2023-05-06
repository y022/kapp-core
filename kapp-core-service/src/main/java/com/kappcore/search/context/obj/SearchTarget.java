package com.kappcore.search.context.obj;

import lombok.Data;

import java.util.Set;

@Data
public class SearchTarget {
    private Set<String> index;
}
