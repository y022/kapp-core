package com.kapp.kappcore.support.mq;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class MqRouteMapping {

    @Getter
    @AllArgsConstructor
    public enum Mapping {
        ADD_ORDER(Exchange.ORDER, Queue.ADD_ORDER, RoutingKey.ADD_ORDER),


        ;
        private final String exchange;
        private final String queue;
        private final String routingKey;

    }

    public static class Exchange {
        public static final String ORDER = "order.exchange";
    }

    public static class Queue {
        public static final String ADD_ORDER = "add_order.queue";
    }

    public static class RoutingKey {
        public static final String ADD_ORDER = "add_order.routingKey";
    }
}
