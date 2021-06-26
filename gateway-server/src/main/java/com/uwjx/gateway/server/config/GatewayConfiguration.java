package com.uwjx.gateway.server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author wanghuan
 * @link https://huan.uwjx.com
 * @date 2021/6/26 13:54
 */
@Configuration
@Slf4j
public class GatewayConfiguration {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder){
        RouteLocatorBuilder.Builder builder = routeLocatorBuilder.routes();
//        builder.route("a-router" , f->f.path("/predicate-cookie/**").uri("http://www.baidu.com"));
//        builder.route("b-router" , f->f.path("/predicate-time/**").uri("lb://www.bing.com"));

//        builder.route("b-router" , f->f.before(
//                ZonedDateTime.of(2021,7,26,
//                        14,56,59,0, ZoneId.systemDefault()))
////                .uri("http://localhost:8012/predicate-cookie/time"));
//                        .uri("http://localhost:8011/predicate-time/time"));
        builder.route("b-router" ,f->f.path("/predicate-time/**")
                .uri("http://localhost:8011"));
//        builder.route("c-router" , f->f.after(
//                ZonedDateTime.of(2021,6,26,
//                        14,56,59,0, ZoneId.systemDefault()))
//                .uri("http://localhost:8011/predicate-time/time"));
        return builder.build();
    }
}
