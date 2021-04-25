package com.poetry.admin.entity;
import java.util.Date;

import com.poetry.admin.base.BizEntity;
import lombok.*;

/**
 * 诗词收藏信息
 * 
 * @author lkl
 * @version 2021-04-20
 */
@Getter
@Setter
public class PoetryUserCollect extends BizEntity {
    /** 主键id */
    private Long id;
    /** 用户code */
    private String appCode;
    /** 诗词id */
    private Long poetryId;
    /** 创建时间 */
    private Date created;
    /** 更新时间 */
    private Date updated;
}