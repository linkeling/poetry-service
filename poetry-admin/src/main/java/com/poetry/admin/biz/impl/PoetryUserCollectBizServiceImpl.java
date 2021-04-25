package com.poetry.admin.biz.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.poetry.admin.BaseBizServiceImpl;
import com.poetry.admin.MyRestTemplate;
import com.poetry.admin.biz.PoetryBaseInfoBizService;
import com.poetry.admin.biz.PoetryUserCollectBizService;
import com.poetry.admin.param.output.PoetryBaseInfoDTO;
import com.poetry.admin.param.output.PoetryUserCollectDTO;
import com.poetry.admin.entity.PoetryUserCollect;
import com.poetry.admin.param.output.WeixinResultDTO;
import com.poetry.admin.param.query.PoetryUserCollectQueryVO;
import com.poetry.admin.param.save.PoetryUserCollectSaveVO;
import com.poetry.admin.service.PoetryUserCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 诗词收藏信息 业务Service 实现类
 *
 * @author lkl
 * @version 2021-04-20
 */
@Service
public class PoetryUserCollectBizServiceImpl extends BaseBizServiceImpl<PoetryUserCollectSaveVO, PoetryUserCollectQueryVO, PoetryUserCollectDTO, PoetryUserCollect, PoetryUserCollectService> implements PoetryUserCollectBizService {
    @Autowired(required = false)
    private MyRestTemplate myRestTemplate;
    @Autowired
    private PoetryBaseInfoBizService poetryBaseInfoBizService;

    private static final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=utf-8";

    @Override
    protected void setCriteriaForQuery(PoetryUserCollectQueryVO vo, QueryWrapper<PoetryUserCollect> criteria) {
        if (Objects.nonNull(vo.getAppCode())) {
            criteria.eq("app_code", vo.getAppCode());
        }
        if (Objects.nonNull(vo.getPoetryId())) {
            criteria.eq("poetry_id", vo.getPoetryId());
        }
    }

    @Override
    public long insert(PoetryUserCollectSaveVO vo) {
        logger.info("新增收藏信息{}" + JSON.toJSONString(vo));
        return super.insert(vo);
    }

    @Override
    public String getUserOpenId(String appCode) {
        //发起微信请求
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        //发起请求
        HttpHeaders headers = new HttpHeaders();
        String appid = "wxad4f91032766fcb1";
        String secret = "9b06660f508a5fd3b6361046eb3fdd0f";
        String grant_type = "authorization_code";
        url = url + "?appid=" + appid + "&secret=" + secret + "&grant_type=" + grant_type + "&js_code=" + appCode;
        String string = myRestTemplate.get(url, headers);
        WeixinResultDTO weixinResultDTO = JSON.parseObject(string, WeixinResultDTO.class);
        return weixinResultDTO.getOpenid();
    }

    @Override
    public List<PoetryBaseInfoDTO> queryPoetryBaseInfoOpenId(String openId) {
        List<PoetryBaseInfoDTO> dtoList = new ArrayList<>();
        PoetryUserCollectQueryVO queryVO = new PoetryUserCollectQueryVO();
        queryVO.setAppCode(openId);
        List<PoetryUserCollectDTO> list = super.queryList(queryVO);
        //遍历id集合
        List<Long> poetryIds = list.stream().map(PoetryUserCollectDTO::getPoetryId).collect(Collectors.toList());
        if (poetryIds.size() > 0) {
            dtoList = poetryBaseInfoBizService.queryByIds(poetryIds);
        }
        return dtoList;
    }


    @Override
    public long queryUserCollectCount(String openId) {
        PoetryUserCollectQueryVO queryVO = new PoetryUserCollectQueryVO();
        queryVO.setAppCode(openId);
        long count = super.count(queryVO);
        return count;
    }

    @Override
    public long queryCount(String openId, long poetryId) {
        PoetryUserCollectQueryVO queryVO = new PoetryUserCollectQueryVO();
        queryVO.setAppCode(openId);
        queryVO.setPoetryId(poetryId);
        long count = super.count(queryVO);
        return count;
    }

    @Override
    public void doDeleteByPoetryId(String appCode, long poetryId) {
        baseService.doDeleteByPoetryId(appCode, poetryId);
    }
}