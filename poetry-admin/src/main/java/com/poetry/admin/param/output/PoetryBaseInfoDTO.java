package com.poetry.admin.param.output;
import com.poetry.admin.base.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 诗词基本信息表DTO
 * 
 * @author lkl
 * @version 2021-04-19
 */
@Data
@EqualsAndHashCode(callSuper=true)
@ApiModel(description = "诗词基本信息表DTO")
public class PoetryBaseInfoDTO extends BaseDTO {

    @ApiModelProperty(value = "分类 1-小学 2-中学 3-课外", position = 1)
    private Integer type;

    @ApiModelProperty(value = "分类 1-小学 2-中学 3-课外", position = 1)
    private String typeName;

    @ApiModelProperty(value = "标题", position = 2)
    private String titleName;

    @ApiModelProperty(value = "朝代", position = 3)
    private String chaodai;

    @ApiModelProperty(value = "作者", position = 4)
    private String author;

    @ApiModelProperty(value = "标签", position = 4 )
    private String label;

    @ApiModelProperty(value = "阅读量", position = 6)
    private Integer readNum;

    @ApiModelProperty(value = "正文", position = 5)
    private String poetryText;

    @ApiModelProperty(value = "译文", position = 2)
    private String translation;

    @ApiModelProperty(value = "注释", position = 3)
    private String notes;

    @ApiModelProperty(value = "赏析", position = 4)
    private String appreciation;

    @ApiModelProperty(value = "是否收藏 0-否 1-是", position = 3)
    private Integer isCollect;

    @ApiModelProperty(value = "创建时间", position = 7)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;

    @ApiModelProperty(value = "更新时间", position = 8)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updated;

    @ApiModelProperty(value = "诗词详情", position = 4)
    private List<PoetryBaseDetailedDTO> detailedList;
}