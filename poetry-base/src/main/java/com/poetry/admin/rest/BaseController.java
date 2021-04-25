package com.poetry.admin.rest;

import com.poetry.admin.base.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * 接口基类
 *
 * @author Negi
 * @version 2019-04
 */

@SuppressWarnings({"unused","unchecked"})
public abstract class BaseController<T extends BaseService,V extends BaseVO,Q extends PaginationVO, D extends BaseDTO> {
    @Autowired
    protected T baseService;
    @Autowired
    protected HttpServletRequest request;

    @ApiOperation(value = "1.01 主键查询", httpMethod = "GET", notes = "1.01 根据id查询数据")
    @ApiImplicitParam(dataType = "string", name="id",value = "主键",example = "1",type="path")
    @GetMapping("/{id}")
    public ResponseDataModel<D> queryById(@PathVariable long id) {
        return ResponseDataModel.ok((D)baseService.queryById(id));
    }

    /**
     * 功能:分页查询
     *
     * @param vo        查询条件
     */
    @ApiOperation(value = "1.02 分页查询", httpMethod = "GET", notes = "1.02 根据参数获取列表")
    @GetMapping
    public ResponseDataModel<Pagination<D>> queryPagination(@Validated Q vo, BindingResult bindingResult) {
        return ResponseDataModel.ok(baseService.queryPagination(vo));
    }
}