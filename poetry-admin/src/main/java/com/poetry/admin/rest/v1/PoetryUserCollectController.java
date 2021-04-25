package com.poetry.admin.rest.v1;
import com.poetry.admin.base.ResponseDataModel;
import com.poetry.admin.param.output.PoetryBaseInfoDTO;
import com.poetry.admin.rest.CrudController;
import com.poetry.admin.biz.PoetryUserCollectBizService;
import com.poetry.admin.param.output.PoetryUserCollectDTO;
import com.poetry.admin.param.query.PoetryUserCollectQueryVO;
import com.poetry.admin.param.save.PoetryUserCollectSaveVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 功能：诗词收藏信息 接口
 * @author lkl
 * @version 2021-04-20
 */
@Api(tags = "poetry-user-collect",description = "诗词收藏信息")
@RestController
@RequestMapping("/v1/poetry-admin/poetry-user-collect")
public class PoetryUserCollectController extends CrudController<PoetryUserCollectBizService, PoetryUserCollectSaveVO, PoetryUserCollectQueryVO, PoetryUserCollectDTO> {

    @ApiOperation(value = "1.02 获取oppenId", httpMethod = "GET", notes = "1.02 获取oppenId")
    @ApiImplicitParam(dataType = "string", name = "appCode", value = "appCode", example = "1", type = "path")
    @GetMapping("/open-id/{appCode}")
    public ResponseDataModel getOpenId(@PathVariable String appCode) {
        return ResponseDataModel.ok(baseService.getUserOpenId(appCode));
    }


    @ApiOperation(value = "1.03 根据openId获取用户收藏", httpMethod = "GET", notes = "1.03 根据openId获取用户收藏")
    @ApiImplicitParam(dataType = "string", name = "openId", value = "openId", example = "1", type = "path")
    @GetMapping("/list-info/{openId}")
    public ResponseDataModel<List<PoetryBaseInfoDTO>> queryPoetryBaseInfoOpenId(@PathVariable String openId) {
        return ResponseDataModel.ok(baseService.queryPoetryBaseInfoOpenId(openId));
    }

    @ApiOperation(value = "1.03 根据openId获取用户收藏", httpMethod = "GET", notes = "1.03 根据openId获取用户收藏")
    @ApiImplicitParam(dataType = "string", name = "openId", value = "openId", example = "1", type = "path")
    @GetMapping("/count-info/{openId}")
    public ResponseDataModel queryUserCollectCount(@PathVariable String openId) {
        return ResponseDataModel.ok(baseService.queryUserCollectCount(openId));
    }


    @ApiOperation(value = "2.05 取消收藏", httpMethod = "DELETE", notes = "2.05 取消收藏")
    @ApiImplicitParam(dataType = "string", name = "poetryId", value = "诗词id", example = "1", type = "path")
    @DeleteMapping(value = "/cancel/{appCode}/{poetryId}")
    public ResponseDataModel doDeleteByPoetryId(@PathVariable String appCode,@PathVariable long poetryId) {
        baseService.doDeleteByPoetryId(appCode,poetryId);
        return ResponseDataModel.ok();
    }
}