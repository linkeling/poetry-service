package com.poetry.admin.entity;
import java.util.Date;

import com.poetry.admin.base.BizEntity;
import lombok.*;

/**
 * 用户表
 * 
 * @author lkl
 * @version 2021-04-23
 */
@Getter
@Setter
public class SysUserInfo extends BizEntity {
    /** 主键 */
    private Long id;
    /** 用户名（默认电话号码） */
    private String userName;
    /** 密码 */
    private String password;
    /** 上次登录时间 */
    private Date lastLoginTime;
    /** 上次登录ip */
    private String lastLoginIp;
    /** 用户类型 1-单位 2-员工 */
    private Integer userType;
    /** 关联的id */
    private Long relationId;
    /** 角色id */
    private Long roleId;
    /** 姓名 */
    private String name;
    /** 联系电话 */
    private String contactsPhone;
    /** 创建人 */
    private Long createUser;
    /** 是否可用 0 不可用 1可用 */
    private Integer valid;
    /** 创建时间 */
    private Date created;
    /** 更新时间 */
    private Date updated;
}