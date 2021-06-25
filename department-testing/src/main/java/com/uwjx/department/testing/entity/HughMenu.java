package com.uwjx.department.testing.entity;

import lombok.Data;

/**
 * @author wanghuan
 * @link https://huan.uwjx.com
 * @date 2021/6/25 22:00
 */
@Data
public class HughMenu {
    private Integer id;

    private Integer pid;

    private Integer level;

    private String name;

    private Integer type;

    private String remark;

}