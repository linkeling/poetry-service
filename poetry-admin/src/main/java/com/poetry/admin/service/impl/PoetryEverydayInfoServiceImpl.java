package com.poetry.admin.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.poetry.admin.dao.PoetryEverydayInfoDAO;
import com.poetry.admin.entity.PoetryEverydayInfo;
import com.poetry.admin.service.PoetryEverydayInfoService;
import org.springframework.stereotype.Service;

/**
 * 每日一首诗词 Service 实现类
 * 
 * @author lkl
 * @version 2021-04-23
 */
@Service
public class PoetryEverydayInfoServiceImpl extends ServiceImpl<PoetryEverydayInfoDAO,PoetryEverydayInfo> implements PoetryEverydayInfoService {

    @Override
    public PoetryEverydayInfo getFirstOne() {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.orderByDesc("created");
        queryWrapper.last("limit 1");
        return baseMapper.selectOne(queryWrapper);
    }
}