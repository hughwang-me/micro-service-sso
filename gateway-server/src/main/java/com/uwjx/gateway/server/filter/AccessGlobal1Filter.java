package com.uwjx.gateway.server.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;

/**
 * @author wanghuan
 * @link https://huan.uwjx.com
 * @date 2021/6/26 17:20
 */
@Slf4j
@Component
@Order(1)
public class AccessGlobal1Filter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.warn("进入 AccessGlobal1Filter");
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().pathWithinApplication().value();
        log.warn("进入 AccessGlobal1Filter path : {}" , path);
        return chain.filter(exchange).then(Mono.fromRunnable(() -> { }));
    }

}
