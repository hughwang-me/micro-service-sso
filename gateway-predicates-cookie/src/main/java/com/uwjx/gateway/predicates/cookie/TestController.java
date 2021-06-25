package com.uwjx.gateway.predicates.cookie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author wanghuan
 * @link https://huan.uwjx.com
 * @date 2021/6/25 17:49
 */
@RestController
@Slf4j
@RequestMapping(value = "time")
public class TestController {

    @GetMapping
    public String test(){
        log.warn("触发 Cookie 转发");
        return "Cookie 转发 : " + new Date().toString();
    }
}
