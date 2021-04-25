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
 * 句子详情信息表VO
 * 
 * @author lkl
 * @version 2021-04-20
 */
@Data
@EqualsAndHashCode(callSuper=true)
@ApiModel(description = "句子详情信息表VO")
public class PoetryBaseDetailedSaveVO extends BaseVO {

    /** 诗词id */
    @ApiModelProperty(value = "诗词id", position = 1 )
    private Long poetryId;
    /** 诗词内容 */
    @ApiModelProperty(value = "诗词内容", position = 2 )
    @Length(max=65535, message = "诗词内容不能大于65535")
    private String content;
}