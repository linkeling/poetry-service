package com.poetry.admin.entity;
import java.util.Date;

import com.poetry.admin.base.BizEntity;
import lombok.*;

/**
 * 每日一首诗词
 * 
 * @author lkl
 * @version 2021-04-23
 */
@Getter
@Setter
public class PoetryEverydayInfo extends BizEntity {
    /** 主键id */
    private Long id;
    /** 诗词id */
    private Long poetryId;
    /** 创建时间 */
    private Date created;
    /** 更新时间 */
    private Date updated;
}