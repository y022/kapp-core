package com.kapp.base.grammer.function;

import java.util.function.Predicate;

public class PredicateV1 {
    private static final Predicate<String> test = (val) -> !val.isEmpty();
}
