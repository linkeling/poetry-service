package com.poetry.admin.param.save;
import com.poetry.admin.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 诗词收藏信息VO
 * 
 * @author lkl
 * @version 2021-04-20
 */
@Data
@EqualsAndHashCode(callSuper=true)
@ApiModel(description = "诗词收藏信息VO")
public class PoetryUserCollectSaveVO extends BaseVO {
    /** 用户code */
    @ApiModelProperty(value = "用户code", position = 1 )
    @Length(max=100, message = "用户code不能大于100")
    private String appCode;
    /** 诗词id */
    @ApiModelProperty(value = "诗词id", position = 2 )
    private Long poetryId;
}