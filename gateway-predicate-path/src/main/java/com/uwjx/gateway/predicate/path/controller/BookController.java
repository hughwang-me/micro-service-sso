package com.uwjx.gateway.predicate.path.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wanghuan
 * @link https://huan.uwjx.com
 * @date 2021/6/25 20:33
 */
@RestController
@Slf4j
@RequestMapping(value = "book")
public class BookController {

    @GetMapping(value = "list")
    public String list(){
        log.warn("触发 book list 请求");
        return " book list 请求";
    }

}
