package com.poetry.admin.entity;
import java.util.Date;

import com.poetry.admin.base.BizEntity;
import lombok.*;

/**
 * 句子详情信息表
 * 
 * @author lkl
 * @version 2021-04-20
 */
@Getter
@Setter
public class PoetryBaseDetailed extends BizEntity {
    /** 主键id */
    private Long id;
    /** 诗词id */
    private Long poetryId;
    /** 诗词内容 */
    private String content;
    /** 创建时间 */
    private Date created;
    /** 更新时间 */
    private Date updated;
}