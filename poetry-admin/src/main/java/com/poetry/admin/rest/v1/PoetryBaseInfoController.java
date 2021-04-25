package com.poetry.admin.rest.v1;
import com.poetry.admin.base.ResponseDataModel;
import com.poetry.admin.biz.PoetryBaseInfoBizService;
import com.poetry.admin.param.output.PoetryBaseInfoDTO;
import com.poetry.admin.param.query.PoetryBaseInfoQueryVO;
import com.poetry.admin.param.save.PoetryBaseInfoSaveVO;
import com.poetry.admin.rest.CrudController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能：诗词基本信息表 接口
 * @author lkl
 * @version 2021-04-19
 */
@Api(tags = "poetry-base-info",description = "诗词基本信息表")
@RestController
@RequestMapping("/v1/poetry-admin/poetry-base-info")
public class PoetryBaseInfoController extends CrudController<PoetryBaseInfoBizService, PoetryBaseInfoSaveVO, PoetryBaseInfoQueryVO, PoetryBaseInfoDTO> {

    @ApiOperation(value = "1.01 主键查询", httpMethod = "GET", notes = "1.01 根据id查询数据")
    @GetMapping("/is-iscollect/{id}/{openId}")
    public ResponseDataModel<PoetryBaseInfoDTO> queryByIdAndOpenId(@PathVariable long id,@PathVariable String openId) {
        return ResponseDataModel.ok((PoetryBaseInfoDTO)baseService.queryByIdAndOpenId(id,openId));
    }

}