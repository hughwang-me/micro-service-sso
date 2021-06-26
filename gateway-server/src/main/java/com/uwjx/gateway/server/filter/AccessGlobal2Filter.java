package com.uwjx.gateway.server.filter;

import com.alibaba.fastjson.JSON;
import com.uwjx.gateway.server.domain.RespDomain;
import com.uwjx.gateway.server.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;

/**
 * @author wanghuan
 * @link https://huan.uwjx.com
 * @date 2021/6/26 17:20
 */
@Slf4j
@Component
@Order(2)
public class AccessGlobal2Filter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.warn("进入 AccessGlobal2Filter");
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String path = request.getPath().pathWithinApplication().value();

        log.info("path: " + request.getPath());
        log.info("address: " + request.getRemoteAddress());
        log.info("method: " + request.getMethodValue());
        log.info("URI: " + request.getURI());

        HttpHeaders headers = request.getHeaders();
        log.info("Headers: " + headers);
        Object requestBody = exchange.getAttribute("cachedRequestBodyObject");
        log.info("body: " + requestBody);

        RespDomain respDomain = new RespDomain();


        String token = request.getHeaders().getFirst("token");
        if (!StringUtils.hasLength(token)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            respDomain.setCode(1);
            respDomain.setMessage("TOKEN 不可为空");
            return getVoidMono(response, respDomain);
        }

        try {
            JWTUtil.verifyToken(token, "secretKey");
        } catch (Exception ex) {
        }

        log.warn("进入 AccessGlobal2Filter path : {}", path);
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
        }));
    }

    private Mono<Void> getVoidMono(ServerHttpResponse serverHttpResponse, Object obj) {
        serverHttpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");

        DataBuffer dataBuffer = serverHttpResponse.bufferFactory().wrap(JSON.toJSONString(obj).getBytes());
        return serverHttpResponse.writeWith(Flux.just(dataBuffer));
    }
}
