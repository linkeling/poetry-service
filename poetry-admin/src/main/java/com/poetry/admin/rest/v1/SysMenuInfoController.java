package com.poetry.admin.rest.v1;
import com.poetry.admin.base.ResponseDataModel;
import com.poetry.admin.biz.SysMenuInfoBizService;
import com.poetry.admin.param.output.SysMenuInfoDTO;
import com.poetry.admin.param.query.SysMenuInfoQueryVO;
import com.poetry.admin.param.save.SysMenuInfoSaveVO;
import com.poetry.admin.rest.CrudController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 功能：系统管理-权限资源表  接口
 * @author lkl
 * @version 2021-04-23
 */
@Api(tags = "sys-menu-info",description = "系统管理-权限资源表 ")
@RestController
@RequestMapping("/v1/poetry-admin/sys-menu-info")
public class SysMenuInfoController extends CrudController<SysMenuInfoBizService, SysMenuInfoSaveVO, SysMenuInfoQueryVO, SysMenuInfoDTO> {

    @ApiOperation(value = "1.03 查询所有菜单", httpMethod = "GET", notes = "1.03 查询所有菜单")
    @GetMapping("/all")
    public ResponseDataModel<List<SysMenuInfoDTO>> queryAll() {
        return ResponseDataModel.ok(baseService.queryAll());
    }
}