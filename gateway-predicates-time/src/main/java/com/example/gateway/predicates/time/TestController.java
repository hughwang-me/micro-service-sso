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

    @GetMapping(value = "t1")
    public String t1(){
        log.warn("触发 时间后 t1 转发");
        return "时间后 t1 转发 : " + new Date().toString();
    }

    @GetMapping(value = "t2")
    public String t2(){
        log.warn("触发 时间后 t2 转发");
        return "时间后 t2 转发 : " + new Date().toString();
    }

    @GetMapping(value = "t3")
    public String t3(){
        log.warn("触发 时间后 t3 转发");
        return "时间后 t3 转发 : " + new Date().toString();
    }
}
