package com.poetry.admin.param.output;
import com.poetry.admin.base.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统管理-权限资源表 DTO
 * 
 * @author lkl
 * @version 2021-04-23
 */
@Data
@EqualsAndHashCode(callSuper=true)
@ApiModel(description = "系统管理-权限资源表 DTO")
public class SysMenuInfoDTO extends BaseDTO {
    /** 上级资源ID */
    @ApiModelProperty(value = "上级资源ID", position = 1)
    private Long parentId;
    /** url */
    @ApiModelProperty(value = "url", position = 2)
    private String frontUrl;
    /** 资源编码 */
    @ApiModelProperty(value = "资源编码", position = 3)
    private String resources;
    /** 资源名称 */
    @ApiModelProperty(value = "资源名称", position = 4)
    private String menuTitle;
    /** 资源级别 */
    @ApiModelProperty(value = "资源级别", position = 5)
    private Integer level;
    /** 排序 */
    @ApiModelProperty(value = "排序", position = 6)
    private Integer sortNo;
    /** 备注 */
    @ApiModelProperty(value = "备注", position = 7)
    private String remarks;

    @ApiModelProperty(value = "子菜单", position = 7)
    private List<SysMenuInfoDTO> childList;
}