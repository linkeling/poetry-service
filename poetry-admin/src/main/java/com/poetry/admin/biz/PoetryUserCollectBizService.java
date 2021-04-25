package com.poetry.admin.biz;
import com.poetry.admin.base.BaseService;
import com.poetry.admin.param.output.PoetryBaseInfoDTO;
import com.poetry.admin.param.output.PoetryUserCollectDTO;
import com.poetry.admin.param.query.PoetryUserCollectQueryVO;
import com.poetry.admin.param.save.PoetryUserCollectSaveVO;

import java.util.List;

/**
 * 诗词收藏信息 业务Service
 * 
 * @author lkl
 * @version 2021-04-20
 */
public interface PoetryUserCollectBizService extends BaseService<PoetryUserCollectSaveVO, PoetryUserCollectQueryVO, PoetryUserCollectDTO> {

    /**
     * 获取openId
     * @return
     */
    String getUserOpenId(String appCode);

    /**
     * 根据openId收藏信息
     * @param openId
     * @return
     */
    List<PoetryBaseInfoDTO>queryPoetryBaseInfoOpenId(String openId);

    /**
     * 根据openId获取收藏条数
     * @param openId
     * @return
     */
    long queryUserCollectCount(String openId);

    /**
     * 查询是个被收藏
     * @param openId
     * @param poetryId
     * @return
     */
    long queryCount(String openId,long poetryId);


    /**
     * 取消收藏
     * @param appCode
     * @param poetryId
     */
    void doDeleteByPoetryId(String appCode,long poetryId);
}