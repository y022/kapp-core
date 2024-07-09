package com.kapp.kappcore.support.mq;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class MqRouteMapping {

    @Getter
    @AllArgsConstructor
    public enum Mapping {
        ADD_ORDER(Exchange.ORDER, Queue.ADD_ORDER, RoutingKey.ADD_ORDER),
        SAVE_BOOK(Exchange.BOOK, Queue.SAVE_BOOK, RoutingKey.SAVE_BOOK),
        SAVE_BOOK_RETRY(Exchange.BOOK, Queue.SAVE_BOOK_RETRY, RoutingKey.SAVE_BOOK_RETRY),


        ;
        private final String exchange;
        private final String queue;
        private final String routingKey;

    }

    public static class Exchange {
        public static final String ORDER = "order.exchange";
        public static final String BOOK = "book.exchange";

    }

    public static class Queue {
        public static final String ADD_ORDER = "add_order.queue";
        public static final String SAVE_BOOK = "saveBook.queue";
        public static final String SAVE_BOOK_RETRY = "saveBook.retry.queue";
    }

    public static class RoutingKey {
        public static final String ADD_ORDER = "add_order.routingKey";
        public static final String SAVE_BOOK = "saveBook.routingKey";
        public static final String SAVE_BOOK_RETRY = "saveBook.retry.routingKey";
    }
}
