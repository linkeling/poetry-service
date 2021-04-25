package com.poetry.admin.biz.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import com.poetry.admin.BaseBizServiceImpl;
import com.poetry.admin.biz.PoetryBaseInfoBizService;
import com.poetry.admin.biz.PoetryEverydayInfoBizService;
import com.poetry.admin.param.output.PoetryBaseInfoDTO;
import com.poetry.admin.param.output.PoetryEverydayInfoDTO;
import com.poetry.admin.entity.PoetryEverydayInfo;
import com.poetry.admin.param.query.PoetryEverydayInfoQueryVO;
import com.poetry.admin.param.save.PoetryEverydayInfoSaveVO;
import com.poetry.admin.service.PoetryEverydayInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Objects;

/**
 * 每日一首诗词 业务Service 实现类
 * 
 * @author lkl
 * @version 2021-04-23
 */
@Service
public class PoetryEverydayInfoBizServiceImpl extends BaseBizServiceImpl<PoetryEverydayInfoSaveVO, PoetryEverydayInfoQueryVO, PoetryEverydayInfoDTO, PoetryEverydayInfo, PoetryEverydayInfoService> implements PoetryEverydayInfoBizService {
    @Autowired
    protected PoetryBaseInfoBizService poetryBaseInfoBizService;
    @Override
    protected void setCriteriaForQuery(PoetryEverydayInfoQueryVO vo, QueryWrapper<PoetryEverydayInfo> criteria) {
        if(Objects.nonNull(vo.getPoetryId())){
            criteria.eq("poetry_id", vo.getPoetryId());
        }
        if(Objects.nonNull(vo.getCreated())){
            criteria.eq("created", vo.getCreated());
        }
        if(Objects.nonNull(vo.getUpdated())){
            criteria.eq("updated", vo.getUpdated());
        }
    }

    @Override
    public PoetryBaseInfoDTO queryFirstOne() {
        PoetryBaseInfoDTO poetryBaseInfoDTO=new PoetryBaseInfoDTO();
        PoetryEverydayInfo everydayInfo=baseService.getFirstOne();
        if (null!=everydayInfo){
            poetryBaseInfoDTO=poetryBaseInfoBizService.queryById(everydayInfo.getPoetryId());
        }
        return poetryBaseInfoDTO;
    }
}