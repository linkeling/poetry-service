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
 * 诗词基本信息表VO
 * 
 * @author lkl
 * @version 2021-04-19
 */
@Data
@EqualsAndHashCode(callSuper=true)
@ApiModel(description = "诗词基本信息表VO")
public class PoetryBaseInfoSaveVO extends BaseVO {

    @ApiModelProperty(value = "分类 1-小学 2-中学 3-课外", position = 1 , required = true)
    @NotNull(message = "分类 1-小学 2-中学 3-课外不能为空")
    private Integer type;

    @ApiModelProperty(value = "标题", position = 2 )
    @Length(max=100, message = "标题不能大于100")
    private String titleName;

    @ApiModelProperty(value = "朝代", position = 3 )
    @Length(max=40, message = "朝代不能大于40")
    private String chaodai;

    @ApiModelProperty(value = "作者", position = 4 )
    @Length(max=50, message = "作者不能大于50")
    private String author;

    @ApiModelProperty(value = "标签", position = 4 )
    private String label;

    @ApiModelProperty(value = "正文", position = 5)
    private String poetryText;

    @ApiModelProperty(value = "阅读量", position = 6 )
    private Integer readNum;

    @ApiModelProperty(value = "译文", position = 2)
    private String translation;

    @ApiModelProperty(value = "注释", position = 3)
    private String notes;

    @ApiModelProperty(value = "赏析", position = 4)
    private String appreciation;

}