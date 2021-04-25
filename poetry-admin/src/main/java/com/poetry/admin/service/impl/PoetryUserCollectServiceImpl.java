package com.poetry.admin.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.poetry.admin.dao.PoetryUserCollectDAO;
import com.poetry.admin.entity.PoetryUserCollect;
import com.poetry.admin.service.PoetryUserCollectService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 诗词收藏信息 Service 实现类
 * 
 * @author lkl
 * @version 2021-04-20
 */
@Service
public class PoetryUserCollectServiceImpl extends ServiceImpl<PoetryUserCollectDAO,PoetryUserCollect> implements PoetryUserCollectService {

    @Override
    public void doDeleteByPoetryId(String appCode,long poetryId) {
        Map<String,Object> paramMap=new HashMap<>(16);
        paramMap.put("app_code",appCode);
        paramMap.put("poetry_id",poetryId);
        int rows=baseMapper.deleteByMap(paramMap);
    }
}