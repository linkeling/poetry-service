package com.poetry.admin.entity;
import java.util.Date;

import com.poetry.admin.base.BizEntity;
import lombok.*;

/**
 * 诗词基本信息表
 * 
 * @author lkl
 * @version 2021-04-19
 */
@Getter
@Setter
public class PoetryBaseInfo extends BizEntity {
    /** 主键id */
    private Long id;
    /** 分类 1-小学 2-中学 3-课外 */
    private Integer type;
    /** 标题 */
    private String titleName;
    /** 朝代 */
    private String chaodai;
    /** 作者 */
    private String author;
    /** 标签 */
    private String label;
    /** 阅读量 */
    private Integer readNum;
    /** 正文 */
    private String poetryText;
    /** 译文 */
    private String translation;
    /** 注释 */
    private String notes;
    /** 赏析 */
    private String appreciation;
    /** 创建时间 */
    private Date created;
    /** 更新时间 */
    private Date updated;
}