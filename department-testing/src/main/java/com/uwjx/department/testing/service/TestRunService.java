package com.uwjx.department.testing.service;

import com.google.gson.Gson;
import com.uwjx.department.testing.entity.HughMenu;
import com.uwjx.department.testing.mapper.HughMenuMapper;
import com.uwjx.department.testing.util.MapTree;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @author wanghuan
 * @link https://huan.uwjx.com
 * @date 2021/6/25 22:04
 */
@Service
@Slf4j
public class TestRunService {

    @Autowired
    HughMenuMapper mapper;

    @PostConstruct
    public void run() {
        Gson gson = new Gson();
        List<HughMenu> menus = mapper.list();
        for (HughMenu menu : menus) {
            log.warn("menu : {}", gson.toJson(menu));
        }

        List<Map<String, Object>> mapList = mapper.listMap();
        for (Map<String, Object> objectMap : mapList) {
            log.warn("menu : {}", gson.toJson(objectMap));
        }

        MapTree mapTree = new MapTree(mapList);
        List<Map<String, Object>> listMap = mapTree.toJsonMap("level");
        log.warn("结果:{}", gson.toJson(listMap));
    }

    public static void main(String[] args) {
        int a = (int)Double.parseDouble("12.9");
        log.warn("a ->{}", a);
    }
}
