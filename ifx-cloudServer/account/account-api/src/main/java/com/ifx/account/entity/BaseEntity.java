package com.ifx.account.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/17
 */
public class BaseEntity {
    @Id
    private Long id;
    /**
     * 创建时间
     */
    @Column("create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime create_time;

    /**
     * 更新时间
     */
    @Column("update_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime update_time;



    /**
     * 删除标志
     */
    @Column("active")
    private Integer active;

    /**
     * 版本
     */
    @Column("version")
    private Integer version;


    private static final long serialVersionUID = 1L;

}
