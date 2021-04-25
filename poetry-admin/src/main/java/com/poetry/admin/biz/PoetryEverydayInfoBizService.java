package com.poetry.admin.biz;
import com.poetry.admin.base.BaseService;
import com.poetry.admin.param.output.PoetryBaseInfoDTO;
import com.poetry.admin.param.output.PoetryEverydayInfoDTO;
import com.poetry.admin.param.query.PoetryEverydayInfoQueryVO;
import com.poetry.admin.param.save.PoetryEverydayInfoSaveVO;

/**
 * 每日一首诗词 业务Service
 * 
 * @author lkl
 * @version 2021-04-23
 */
public interface PoetryEverydayInfoBizService extends BaseService<PoetryEverydayInfoSaveVO, PoetryEverydayInfoQueryVO, PoetryEverydayInfoDTO> {
    /**
     * 每日一首
     * @return
     */
    PoetryBaseInfoDTO queryFirstOne();
}