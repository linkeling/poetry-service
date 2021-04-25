package com.poetry.admin.rest.v1;
import com.poetry.admin.biz.PoetryBaseDetailedBizService;
import com.poetry.admin.param.output.PoetryBaseDetailedDTO;
import com.poetry.admin.param.query.PoetryBaseDetailedQueryVO;
import com.poetry.admin.param.save.PoetryBaseDetailedSaveVO;
import com.poetry.admin.rest.CrudController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能：句子详情信息表 接口
 * @author lkl
 * @version 2021-04-20
 */
@Api(tags = "poetry-base-detailed",description = "句子详情信息表")
@RestController
@RequestMapping("/v1/poetry-admin/poetry-base-detailed")
public class PoetryBaseDetailedController extends CrudController<PoetryBaseDetailedBizService, PoetryBaseDetailedSaveVO, PoetryBaseDetailedQueryVO, PoetryBaseDetailedDTO> {

}