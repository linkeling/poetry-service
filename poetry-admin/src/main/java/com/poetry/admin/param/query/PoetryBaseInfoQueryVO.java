package com.poetry.admin.param.query;
import com.poetry.admin.base.PaginationVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询诗词基本信息表VO
 * 
 * @author lkl
 * @version 2021-04-19
 */
@Data
@EqualsAndHashCode(callSuper=true)
@ApiModel(description = "查询诗词基本信息表VO")
public class PoetryBaseInfoQueryVO extends PaginationVO {
    /** 分类 1-小学 2-中学 3-课外 */
    @ApiModelProperty(value = "分类 1-小学 2-中学 3-课外", position = 1)
    private Integer type;

    /** 标题 */
    @ApiModelProperty(value = "标题", position = 2)
    private String titleName;

    /** 朝代 */
    @ApiModelProperty(value = "朝代", position = 3)
    private String chaodai;

    /** 作者 */
    @ApiModelProperty(value = "作者", position = 4)
    private String author;


    @ApiModelProperty(value = "标签", position = 4 )
    private String label;


    /** 阅读量 */
    @ApiModelProperty(value = "阅读量", position = 6)
    private Integer readNum;

}