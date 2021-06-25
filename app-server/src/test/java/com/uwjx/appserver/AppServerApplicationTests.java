package com.uwjx.appserver;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uwjx.appserver.model.RxUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Slf4j
class AppServerApplicationTests {

    @Test
    void testObject() {
        RxUser rxUser = new RxUser();
        rxUser.setId(1);
        rxUser.setUsername("wanghuan");
        rxUser.setPassword("123456");

        Gson gson = new Gson();

        String userStr = gson.toJson(rxUser);
        log.warn("生成的String:{}", userStr);

        String msg = "{\"id\":1,\"username\":\"wanghuan\",\"password\":\"123456\"}";

        RxUser user = gson.fromJson(msg, RxUser.class);
        log.warn("解析：{}", gson.toJson(user));

    }

    @Test
    void listObject() {
        List<RxUser> list = new ArrayList<RxUser>();
        for (int i = 0; i < 3; i++) {
            RxUser rxUser = new RxUser();
            rxUser.setId(i);
            rxUser.setUsername("wanghuan" + i);
            rxUser.setPassword("123456");
            list.add(rxUser);
        }

        Gson gson = new Gson();

        String userListStr = gson.toJson(list);
        log.warn("生成的String:{}", userListStr);

//        String msg = "{\"id\":1,\"username\":\"wanghuan\",\"password\":\"123456\"}";
//
//        RxUser user = gson.fromJson(msg , RxUser.class);
//        log.warn("解析：{}" , gson.toJson(user));

        String msg = "[{\"id\":0,\"username\":\"wanghuan0\",\"password\":\"123456\"},{\"id\":1,\"username\":\"wanghuan1\",\"password\":\"123456\"},{\"id\":2,\"username\":\"wanghuan2\",\"password\":\"123456\"}]\n";
        Type type = new TypeToken<ArrayList<RxUser>>() {
        }.getType();
        List<RxUser> rxUsers = gson.fromJson(msg, type);
        for (RxUser rxUser : rxUsers) {
            log.warn("生成的String:{}", rxUser.toString());
        }
    }

}
