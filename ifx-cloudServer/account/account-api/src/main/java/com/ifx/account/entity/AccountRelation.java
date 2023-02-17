package com.ifx.account.entity;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

/**
 * 账户关系表
 * @TableName account_relation
 */
//@TableName(value ="account_relation")
@Data
@Table("account_relation")
public class AccountRelation extends BaseEntity implements Serializable {


    /**
     * 用户id
     */
    @Column(value = "account")
    private String account;

    /**
     * 账号关系集合
     */
    @Column(value = "account_relations")
    private String accountRelations;



//    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}