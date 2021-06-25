package com.example.gateway.predicates.time;

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
        log.warn("触发 时间后 转发");
        return "时间后转发 : " + new Date().toString();
    }
}
