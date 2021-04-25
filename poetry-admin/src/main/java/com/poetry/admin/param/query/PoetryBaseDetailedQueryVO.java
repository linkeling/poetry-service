package com.poetry.admin.param.query;
import com.poetry.admin.base.PaginationVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询句子详情信息表VO
 * 
 * @author lkl
 * @version 2021-04-20
 */
@Data
@EqualsAndHashCode(callSuper=true)
@ApiModel(description = "查询句子详情信息表VO")
public class PoetryBaseDetailedQueryVO extends PaginationVO {

    /** 诗词id */
    @ApiModelProperty(value = "诗词id", position = 1)
    private Long poetryId;

    @ApiModelProperty(value = "诗词id", position = 1)
    private List<Long> poetryIdList;

    /** 诗词内容 */
    @ApiModelProperty(value = "诗词内容", position = 2)
    private String content;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 3)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 4)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updated;
}