package com.poetry.admin.biz.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.poetry.admin.BaseBizServiceImpl;
import com.poetry.admin.biz.PoetryBaseDetailedBizService;
import com.poetry.admin.param.output.PoetryBaseDetailedDTO;
import com.poetry.admin.entity.PoetryBaseDetailed;
import com.poetry.admin.param.query.PoetryBaseDetailedQueryVO;
import com.poetry.admin.param.save.PoetryBaseDetailedSaveVO;
import com.poetry.admin.service.PoetryBaseDetailedService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 句子详情信息表 业务Service 实现类
 *
 * @author lkl
 * @version 2021-04-20
 */
@Service
public class PoetryBaseDetailedBizServiceImpl extends BaseBizServiceImpl<PoetryBaseDetailedSaveVO, PoetryBaseDetailedQueryVO, PoetryBaseDetailedDTO, PoetryBaseDetailed, PoetryBaseDetailedService> implements PoetryBaseDetailedBizService {
    @Override
    protected void setCriteriaForQuery(PoetryBaseDetailedQueryVO vo, QueryWrapper<PoetryBaseDetailed> criteria) {
        if (Objects.nonNull(vo.getPoetryId())) {
            criteria.eq("poetry_id", vo.getPoetryId());
        }
        if (Objects.nonNull(vo.getPoetryId())) {
            criteria.eq("poetry_id", vo.getPoetryId());
        }
        if (Objects.nonNull(vo.getContent())) {
            criteria.eq("content", vo.getContent());
        }
        if (Objects.nonNull(vo.getCreated())) {
            criteria.eq("created", vo.getCreated());
        }
        if (Objects.nonNull(vo.getUpdated())) {
            criteria.eq("updated", vo.getUpdated());
        }
    }

    @Override
    public Map<Long, List<PoetryBaseDetailedDTO>> queryDetailedMapByPoetryId(List<Long> poetryIds) {
        Map<Long, List<PoetryBaseDetailedDTO>> detailedMap = new HashMap<>();
        PoetryBaseDetailedQueryVO vo = new PoetryBaseDetailedQueryVO();
        vo.setPoetryIdList(poetryIds);
        List<PoetryBaseDetailedDTO> dtoList = super.queryList(vo);
        poetryIds.forEach(x -> {
            List<PoetryBaseDetailedDTO> dtos = new ArrayList<>();
            dtoList.forEach(y -> {
                if (x.equals(y.getPoetryId())) {
                    dtos.add(y);
                }
            });
            detailedMap.put(x, dtos);
        });
        return detailedMap;
    }


    @Override
    public List<PoetryBaseDetailedDTO> queryDetailedByPoetryId(long poetryId) {
        PoetryBaseDetailedQueryVO vo = new PoetryBaseDetailedQueryVO();
        vo.setPoetryId(poetryId);
        List<PoetryBaseDetailedDTO> dtoList = super.queryList(vo);
        return dtoList;
    }
}