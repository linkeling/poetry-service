package com.poetry.admin.param.output;
import com.poetry.admin.base.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户表DTO
 * 
 * @author lkl
 * @version 2021-04-23
 */
@Data
@EqualsAndHashCode(callSuper=true)
@ApiModel(description = "用户表DTO")
public class SysUserInfoDTO extends BaseDTO {
    /** 主键 */
    @ApiModelProperty(value = "主键")
    private Long id;
    /** 用户名（默认电话号码） */
    @ApiModelProperty(value = "用户名（默认电话号码）", position = 1)
    private String userName;
    /** 密码 */
    @ApiModelProperty(value = "密码", position = 2)
    private String password;
    /** 上次登录时间 */
    @ApiModelProperty(value = "上次登录时间", position = 3)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;
    /** 上次登录ip */
    @ApiModelProperty(value = "上次登录ip", position = 4)
    private String lastLoginIp;
    /** 用户类型 1-单位 2-员工 */
    @ApiModelProperty(value = "用户类型 1-单位 2-员工", position = 5)
    private Integer userType;
    /** 关联的id */
    @ApiModelProperty(value = "关联的id", position = 6)
    private Long relationId;
    /** 角色id */
    @ApiModelProperty(value = "角色id", position = 7)
    private Long roleId;
    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 8)
    private String name;
    /** 联系电话 */
    @ApiModelProperty(value = "联系电话", position = 9)
    private String contactsPhone;
    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 10)
    private Long createUser;
    /** 是否可用 0 不可用 1可用 */
    @ApiModelProperty(value = "是否可用 0 不可用 1可用", position = 11)
    private Integer valid;
    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 12)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;
    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 13)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updated;
}