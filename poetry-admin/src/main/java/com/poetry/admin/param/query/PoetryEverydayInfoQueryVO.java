package com.poetry.admin.param.query;
import com.poetry.admin.base.PaginationVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询每日一首诗词VO
 * 
 * @author lkl
 * @version 2021-04-23
 */
@Data
@EqualsAndHashCode(callSuper=true)
@ApiModel(description = "查询每日一首诗词VO")
public class PoetryEverydayInfoQueryVO extends PaginationVO {

    /** 主键id */
    @ApiModelProperty(value = "主键id")
    private Long id;

    /** 诗词id */
    @ApiModelProperty(value = "诗词id", position = 1)
    private Long poetryId;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 2)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 3)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updated;
}