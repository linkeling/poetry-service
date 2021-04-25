package com.poetry.admin.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel(value="jwt返回的实体")
public class JwtTokenResult {
    @ApiModelProperty(value="access_token")
    private String access_token;

    @ApiModelProperty(value="刷新token")
    private String refresh_token;

    @ApiModelProperty(value="过期时间")
    private String expire_time;

    @ApiModelProperty(value="token_type")
    private String token_type;

}
