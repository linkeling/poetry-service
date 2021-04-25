package com.poetry.admin.rest.v1;
import com.poetry.admin.base.ResponseDataModel;
import com.poetry.admin.biz.PoetryEverydayInfoBizService;
import com.poetry.admin.param.output.PoetryBaseInfoDTO;
import com.poetry.admin.param.output.PoetryEverydayInfoDTO;
import com.poetry.admin.param.query.PoetryEverydayInfoQueryVO;
import com.poetry.admin.param.save.PoetryEverydayInfoSaveVO;
import com.poetry.admin.rest.CrudController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能：每日一首诗词 接口
 * @author lkl
 * @version 2021-04-23
 */
@Api(tags = "poetry-everyday-info",description = "每日一首诗词")
@RestController
@RequestMapping("/v1/poetry-admin/poetry-everyday-info")
public class PoetryEverydayInfoController extends CrudController<PoetryEverydayInfoBizService, PoetryEverydayInfoSaveVO, PoetryEverydayInfoQueryVO, PoetryEverydayInfoDTO> {

    @ApiOperation(value = "1.01 主键查询", httpMethod = "GET", notes = "1.01 根据id查询数据")
    @GetMapping("/first-one")
    public ResponseDataModel<PoetryBaseInfoDTO> queryFirstOne() {
        return ResponseDataModel.ok((PoetryBaseInfoDTO)baseService.queryFirstOne());
    }
}