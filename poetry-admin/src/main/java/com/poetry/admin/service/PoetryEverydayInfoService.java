package com.poetry.admin.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.poetry.admin.entity.PoetryEverydayInfo;

/**
 * 每日一首诗词 Service
 * 
 * @author lkl
 * @version 2021-04-23
 */
public interface PoetryEverydayInfoService extends IService<PoetryEverydayInfo> {

    /**
     * 每日一首
     * @return
     */
    PoetryEverydayInfo getFirstOne();
}