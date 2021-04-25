package com.poetry.admin.rest;

import com.poetry.admin.base.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 包含CRUD操作的接口基类
 *
 * @author Negi
 * @version 2019-04
 */

@SuppressWarnings({"unused", "unchecked"})
public abstract class CrudController<T extends BaseService, V extends BaseVO, Q extends PaginationVO, D extends BaseDTO> extends BaseController {
    @Autowired
    protected T baseService;
    @Autowired
    protected HttpServletRequest request;

    @ApiOperation(value = "2.01 新增", httpMethod = "POST", notes = "2.01 新增数据", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping
    public ResponseDataModel doInsert(@Validated @RequestBody V vo, BindingResult bindingResult) {
        long id = baseService.insert(vo);
        return ResponseDataModel.ok(id);
    }

    @ApiOperation(value = "2.02 修改", httpMethod = "PUT", notes = "2.02 修改数据", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParam(dataType = "string", name = "id", value = "主键", example = "1", type = "path")
    @PutMapping(value = "/{id}")
    public ResponseDataModel doUpdate(@PathVariable long id, @Validated @RequestBody V vo, BindingResult bindingResult) {
        vo.setId(id);
        baseService.updateSelective(vo);
        return ResponseDataModel.ok();
    }

//    @ApiOperation(value = "2.03 部分修改", httpMethod = "PATCH", notes = "2.03 修改部分数据",consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ApiImplicitParam(dataType = "string", name="id",value = "主键",example = "1",type="path")
//    @PatchMapping(value = "/{id}")
//    public ResponseDataModel doUpdateSelective(@PathVariable long id,@Validated @RequestBody V vo, BindingResult bindingResult) {
//        vo.setId(id);
//        baseService.updateSelective(vo);
//        return ResponseDataModel.ok();
//    }

    /**
     * 屏蔽删除接口，尽量只做逻辑删除
     * @param id
     * @return
     */
    @ApiOperation(value = "2.04 数据删除", httpMethod = "DELETE", notes = "2.04 删除数据")
    @ApiImplicitParam(dataType = "string", name = "id", value = "主键", example = "1", type = "path")
    @DeleteMapping(value = "/{id}")
    public ResponseDataModel doDelete(@PathVariable long id) {
        baseService.deleteById(id);
        return ResponseDataModel.ok();
    }
}