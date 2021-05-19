package com.uwjx.authserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(value = "auth")
public class AuthController {

    @GetMapping
    public String get(){
        String resp = "AuthController - get - resp !";
        log.warn(resp);
        return resp;
    }

    @PostMapping
    public String post(){
        String resp = "AuthController - post - resp !";
        log.warn(resp);
        return resp;
    }

    @PutMapping
    public String put(){
        String resp = "AuthController - put - resp !";
        log.warn(resp);
        return resp;
    }

    @DeleteMapping
    public String delete(){
        String resp = "AuthController - delete - resp !";
        log.warn(resp);
        return resp;
    }
}
