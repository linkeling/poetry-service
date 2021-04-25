package com.poetry.admin.biz;
import com.poetry.admin.base.BaseService;
import com.poetry.admin.param.output.PoetryBaseDetailedDTO;
import com.poetry.admin.param.query.PoetryBaseDetailedQueryVO;
import com.poetry.admin.param.save.PoetryBaseDetailedSaveVO;

import java.util.List;
import java.util.Map;

/**
 * 句子详情信息表 业务Service
 * 
 * @author lkl
 * @version 2021-04-20
 */
public interface PoetryBaseDetailedBizService extends BaseService<PoetryBaseDetailedSaveVO, PoetryBaseDetailedQueryVO, PoetryBaseDetailedDTO> {

    /**
     * 根据诗词id查询诗词详情信息
     * @param poetryIds
     * @return
     */
    Map<Long,List<PoetryBaseDetailedDTO>> queryDetailedMapByPoetryId(List<Long> poetryIds);


    List<PoetryBaseDetailedDTO> queryDetailedByPoetryId(long poetryId);
}