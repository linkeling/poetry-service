package com.poetry.admin.biz;
import com.poetry.admin.base.BaseService;
import com.poetry.admin.param.output.PoetryBaseInfoDTO;
import com.poetry.admin.param.query.PoetryBaseInfoQueryVO;
import com.poetry.admin.param.save.PoetryBaseInfoSaveVO;

import java.util.List;

/**
 * 诗词基本信息表 业务Service
 * 
 * @author lkl
 * @version 2021-04-19
 */
public interface PoetryBaseInfoBizService extends BaseService<PoetryBaseInfoSaveVO, PoetryBaseInfoQueryVO, PoetryBaseInfoDTO> {


    /**
     * 根据id获取信息
     * @param ids
     * @return
     */
    List<PoetryBaseInfoDTO> queryByIds(List<Long> ids);


    /**
     * 查询是否收藏
     * @param id
     * @param openId
     * @return
     */
    PoetryBaseInfoDTO queryByIdAndOpenId(long id,String openId);
}