package com.poetry.admin.rest.v1;
import com.poetry.admin.NoToken;
import com.poetry.admin.base.ResponseDataModel;
import com.poetry.admin.biz.SysUserInfoBizService;
import com.poetry.admin.param.output.SysUserInfoDTO;
import com.poetry.admin.param.query.SysUserInfoQueryVO;
import com.poetry.admin.param.save.SysUserInfoSaveVO;
import com.poetry.admin.rest.CrudController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能：用户表 接口
 * @author lkl
 * @version 2021-04-23
 */
@Api(tags = "sys-user-info",description = "用户表")
@RestController
@RequestMapping("/v1/poetry-admin/sys-user-info")
public class SysUserInfoController extends CrudController<SysUserInfoBizService, SysUserInfoSaveVO, SysUserInfoQueryVO, SysUserInfoDTO> {

    @ApiOperation(value = "1.01 用户登录", httpMethod = "POST", notes = "1.01 用户登录",consumes = MediaType.APPLICATION_JSON_VALUE)
    @NoToken
    @PostMapping("/login")
    public ResponseDataModel userLogin(@RequestBody SysUserInfoQueryVO vo) {
        return ResponseDataModel.ok(baseService.userLogin(vo.getUserName(), vo.getPassword()));
    }
}