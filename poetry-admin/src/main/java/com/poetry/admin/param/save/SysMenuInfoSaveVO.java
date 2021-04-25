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
 * 系统管理-权限资源表 VO
 * 
 * @author lkl
 * @version 2021-04-23
 */
@Data
@EqualsAndHashCode(callSuper=true)
@ApiModel(description = "系统管理-权限资源表 VO")
public class SysMenuInfoSaveVO extends BaseVO {
    /** 主键id */
    @ApiModelProperty(value = "主键id" , required = true)
    @NotNull(message = "主键id不能为空")
    private Long id;
    /** 上级资源ID */
    @ApiModelProperty(value = "上级资源ID", position = 1 )
    private Long parentId;
    /** url */
    @ApiModelProperty(value = "url", position = 2 )
    @Length(max=100, message = "url不能大于100")
    private String frontUrl;
    /** 资源编码 */
    @ApiModelProperty(value = "资源编码", position = 3 )
    @Length(max=100, message = "资源编码不能大于100")
    private String resources;
    /** 资源名称 */
    @ApiModelProperty(value = "资源名称", position = 4 )
    @Length(max=100, message = "资源名称不能大于100")
    private String menuTitle;
    /** 资源级别 */
    @ApiModelProperty(value = "资源级别", position = 5 )
    private Integer level;
    /** 排序 */
    @ApiModelProperty(value = "排序", position = 6 )
    private Integer sortNo;
    /** 备注 */
    @ApiModelProperty(value = "备注", position = 7 )
    @Length(max=500, message = "备注不能大于500")
    private String remarks;
    /** 添加时间 */
    @ApiModelProperty(value = "添加时间", position = 8 , required = true)
    @NotNull(message = "添加时间不能为空")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;
    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 9 , required = true)
    @NotNull(message = "更新时间不能为空")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updated;
}