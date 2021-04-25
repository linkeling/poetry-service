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
 * 用户表VO
 * 
 * @author lkl
 * @version 2021-04-23
 */
@Data
@EqualsAndHashCode(callSuper=true)
@ApiModel(description = "用户表VO")
public class SysUserInfoSaveVO extends BaseVO {
    /** 用户名（默认电话号码） */
    @ApiModelProperty(value = "用户名（默认电话号码）", position = 1 , required = true)
    @NotNull(message = "用户名（默认电话号码）不能为空")
    @Length(max=64, message = "用户名（默认电话号码）不能大于64")
    private String userName;
    /** 密码 */
    @ApiModelProperty(value = "密码", position = 2 , required = true)
    @NotNull(message = "密码不能为空")
    @Length(max=64, message = "密码不能大于64")
    private String password;
    /** 上次登录时间 */
    @ApiModelProperty(value = "上次登录时间", position = 3 )
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;
    /** 上次登录ip */
    @ApiModelProperty(value = "上次登录ip", position = 4 )
    @Length(max=64, message = "上次登录ip不能大于64")
    private String lastLoginIp;
    /** 用户类型 1-单位 2-员工 */
    @ApiModelProperty(value = "用户类型 1-单位 2-员工", position = 5 )
    private Integer userType;
    /** 关联的id */
    @ApiModelProperty(value = "关联的id", position = 6 )
    private Long relationId;
    /** 角色id */
    @ApiModelProperty(value = "角色id", position = 7 )
    private Long roleId;
    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 8 )
    @Length(max=100, message = "姓名不能大于100")
    private String name;
    /** 联系电话 */
    @ApiModelProperty(value = "联系电话", position = 9 )
    @Length(max=255, message = "联系电话不能大于255")
    private String contactsPhone;
    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 10 )
    private Long createUser;
    /** 是否可用 0 不可用 1可用 */
    @ApiModelProperty(value = "是否可用 0 不可用 1可用", position = 11 )
    private Integer valid;
}