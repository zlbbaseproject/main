package com.zbin.config;

public class MQBinding {
    private String routeKey;
    private String queue;
    private String exchange;
    private Long ttl;
    private String exchangeType;
    private String vHost;

    public String getRouteKey() {
        return routeKey;
    }

    public MQBinding setRouteKey(String routeKey) {
        this.routeKey = routeKey;
        return this;
    }

    public String getQueue() {
        return queue;
    }

    public MQBinding setQueue(String queue) {
        this.queue = queue;
        return this;
    }

    public String getExchange() {
        return exchange;
    }

    public MQBinding setExchange(String exchange) {
        this.exchange = exchange;
        return this;
    }

    public Long getTtl() {
        return ttl;
    }

    public MQBinding setTtl(Long ttl) {
        this.ttl = ttl;
        return this;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public MQBinding setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
        return this;
    }

    public String getvHost() {
        return vHost;
    }

    public MQBinding setvHost(String vHost) {
        this.vHost = vHost;
        return this;
    }

    @Override
    public String toString() {
        return "MQBinding{" +
                "routeKey='" + routeKey + '\'' +
                ", queue='" + queue + '\'' +
                ", exchange='" + exchange + '\'' +
                ", ttl=" + ttl +
                ", exchangeType='" + exchangeType + '\'' +
                ", vHost='" + vHost + '\'' +
                '}';
    }
}
