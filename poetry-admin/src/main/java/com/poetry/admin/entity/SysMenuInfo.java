package com.poetry.admin.entity;
import java.util.Date;

import com.poetry.admin.base.BizEntity;
import lombok.*;

/**
 * 系统管理-权限资源表 
 * 
 * @author lkl
 * @version 2021-04-23
 */
@Getter
@Setter
public class SysMenuInfo extends BizEntity {
    /** 主键id */
    private Long id;
    /** 上级资源ID */
    private Long parentId;
    /** url */
    private String frontUrl;
    /** 资源编码 */
    private String resources;
    /** 资源名称 */
    private String menuTitle;
    /** 资源级别 */
    private Integer level;
    /** 排序 */
    private Integer sortNo;
    /** 备注 */
    private String remarks;
    /** 添加时间 */
    private Date created;
    /** 更新时间 */
    private Date updated;
}