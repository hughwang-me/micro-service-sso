package com.uwjx.department.testing.mapper;

import com.uwjx.department.testing.entity.HughMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author wanghuan
 * @link https://huan.uwjx.com
 * @date 2021/6/25 22:00
 */

@Mapper
public interface HughMenuMapper {

    List<HughMenu> list();

    List<Map<String , Object>> listMap();
}