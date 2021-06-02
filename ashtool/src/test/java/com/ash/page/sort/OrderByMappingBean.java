package com.ash.page.sort;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author jianshengfei
 * @version 1.0.0
 * @ClassName OrderByMappingBean.java
 * @Description 排序字段映射测试Bean
 * @createTime 2021年06月02日 15:43
 */
@Data
public class OrderByMappingBean {

    private String userName;

    private String password;

    private LocalDateTime createDateTime;

    private LocalDateTime registerDateTime;


}
