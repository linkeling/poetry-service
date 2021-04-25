package com.poetry.admin.param.query;
import com.poetry.admin.base.PaginationVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询诗词收藏信息VO
 * 
 * @author lkl
 * @version 2021-04-20
 */
@Data
@EqualsAndHashCode(callSuper=true)
@ApiModel(description = "查询诗词收藏信息VO")
public class PoetryUserCollectQueryVO extends PaginationVO {
    /** 用户code */
    @ApiModelProperty(value = "用户code", position = 1)
    private String appCode;

    /** 诗词id */
    @ApiModelProperty(value = "诗词id", position = 2)
    private Long poetryId;

}