package com.uwjx.gateway.predicate.path.controller;

import com.uwjx.gateway.predicate.path.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author wanghuan
 * @link https://huan.uwjx.com
 * @date 2021/6/25 20:33
 */
@RestController
@Slf4j
@RequestMapping(value = "method")
public class MethodController {

    @GetMapping(value = "get")
    public String get(@RequestParam("key1")String key1 , @RequestParam("key2")String key2 ){
        log.warn("触发 method get 请求 key1 -> {} ,key2 -> {}" , key1 , key2);
        return key1 + " | " + key2;
    }

    @PostMapping(value = "post")
    public String post(@RequestBody Book book){
        log.warn("keys -> {}" , book.toString());
        return book.toString();
    }

}
