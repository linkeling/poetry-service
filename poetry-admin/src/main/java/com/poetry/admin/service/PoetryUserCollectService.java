package com.poetry.admin.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.poetry.admin.entity.PoetryUserCollect;

/**
 * 诗词收藏信息 Service
 * 
 * @author lkl
 * @version 2021-04-20
 */
public interface PoetryUserCollectService extends IService<PoetryUserCollect> {

    /**
     * 取消收藏
     * @param poetryId
     * @param appCode
     */
    void doDeleteByPoetryId(String appCode,long poetryId);
}