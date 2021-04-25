package com.poetry.admin.biz.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.poetry.admin.BaseBizServiceImpl;
import com.poetry.admin.base.Pagination;
import com.poetry.admin.biz.PoetryBaseDetailedBizService;
import com.poetry.admin.biz.PoetryBaseInfoBizService;
import com.poetry.admin.biz.PoetryUserCollectBizService;
import com.poetry.admin.enums.EnumPoetryType;
import com.poetry.admin.param.output.PoetryBaseDetailedDTO;
import com.poetry.admin.param.output.PoetryBaseInfoDTO;
import com.poetry.admin.entity.PoetryBaseInfo;
import com.poetry.admin.param.query.PoetryBaseInfoQueryVO;
import com.poetry.admin.param.save.PoetryBaseDetailedSaveVO;
import com.poetry.admin.param.save.PoetryBaseInfoSaveVO;
import com.poetry.admin.service.PoetryBaseInfoService;
import javassist.expr.NewExpr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 诗词基本信息表 业务Service 实现类
 *
 * @author lkl
 * @version 2021-04-19
 */
@Service
public class PoetryBaseInfoBizServiceImpl extends BaseBizServiceImpl<PoetryBaseInfoSaveVO, PoetryBaseInfoQueryVO, PoetryBaseInfoDTO, PoetryBaseInfo, PoetryBaseInfoService> implements PoetryBaseInfoBizService {
    @Autowired
    private PoetryBaseDetailedBizService detailedBizService;
    @Autowired
    private PoetryUserCollectBizService poetryUserCollectBizService;

    @Override
    protected void setCriteriaForQuery(PoetryBaseInfoQueryVO vo, QueryWrapper<PoetryBaseInfo> criteria) {
        if (Objects.nonNull(vo.getType())) {
            criteria.eq("type", vo.getType());
        }
        if (!StrUtil.isBlank(vo.getTitleName())) {
            criteria.like("title_name", vo.getTitleName());
        }
        if (Objects.nonNull(vo.getChaodai())) {
            criteria.eq("chaodai", vo.getChaodai());
        }
        if (Objects.nonNull(vo.getAuthor())) {
            criteria.eq("author", vo.getAuthor());
        }
        if (Objects.nonNull(vo.getLabel())) {
            criteria.eq("label", vo.getLabel());
        }
        if (Objects.nonNull(vo.getReadNum())) {
            criteria.eq("read_num", vo.getReadNum());
        }
    }


    @Override
    public long insert(PoetryBaseInfoSaveVO vo) {
        vo.setReadNum(0);
        long id=super.insert(vo);
        List<String> list = Arrays.asList(vo.getPoetryText().split("。"));
        List<PoetryBaseDetailedSaveVO> saveList=new ArrayList<>();
        list.forEach(x->{
            PoetryBaseDetailedSaveVO saveVO=new PoetryBaseDetailedSaveVO();
            saveVO.setPoetryId(id);
            saveVO.setContent(x);
            saveList.add(saveVO);
        });
        detailedBizService.insertBatch(saveList);
        return id;
    }

    @Override
    public PoetryBaseInfoDTO queryById(long id) {
        PoetryBaseInfoDTO dto = super.queryById(id);
        List<PoetryBaseDetailedDTO> dtoList=detailedBizService.queryDetailedByPoetryId(id);
        dto.setDetailedList(dtoList);
        //查询是否被收藏
        long count=poetryUserCollectBizService.queryCount("oyT1R5aWL5rfWQnOHqa9Q4RBUGxs",id);
        int isCollect=0;
        if (count>0){
            isCollect=1;
        }
        dto.setIsCollect(isCollect);
        return dto;
    }

    @Override
    public List<PoetryBaseInfoDTO> queryByIds(List<Long> ids) {
        List<PoetryBaseInfoDTO> dtoList=super.queryListByIds(ids);
        Map<Long, List<PoetryBaseDetailedDTO>> detailedMap = detailedBizService.queryDetailedMapByPoetryId(ids);
        dtoList.forEach(x -> {
            List<PoetryBaseDetailedDTO> detailedList = detailedMap.get(x.getId());
            if (detailedList.size() > 0) {
                x.setDetailedList(detailedList);
            }
        });
        return dtoList;
    }

    @Override
    public PoetryBaseInfoDTO queryByIdAndOpenId(long id,String openId) {
        PoetryBaseInfoDTO dto = super.queryById(id);
        List<PoetryBaseDetailedDTO> dtoList=detailedBizService.queryDetailedByPoetryId(id);
        dto.setDetailedList(dtoList);
        //查询是否被收藏
        long count=poetryUserCollectBizService.queryCount(openId,id);
        int isCollect=0;
        if (count>0){
            isCollect=1;
        }
        dto.setIsCollect(isCollect);
        //更新阅读次数
        PoetryBaseInfoSaveVO saveVO= new PoetryBaseInfoSaveVO();
        saveVO.setId(id);
        saveVO.setReadNum(dto.getReadNum()+1);
        super.updateSelective(saveVO);
        return dto;
    }

    @Override
    public Pagination<PoetryBaseInfoDTO> queryPagination(PoetryBaseInfoQueryVO vo) {
        Pagination<PoetryBaseInfoDTO> pagination = super.queryPagination(vo);
        //遍历取id
        List<Long> ids = pagination.getDataList().stream().map(PoetryBaseInfoDTO::getId).collect(Collectors.toList());
        //查询详情
        Map<Long, List<PoetryBaseDetailedDTO>> detailedMap = detailedBizService.queryDetailedMapByPoetryId(ids);
        pagination.getDataList().forEach(x -> {
            List<PoetryBaseDetailedDTO> dtoList = detailedMap.get(x.getId());
            if (dtoList.size() > 0) {
                x.setDetailedList(dtoList);
            }
            x.setTypeName(EnumPoetryType.getName(x.getType()));
        });
        return pagination;
    }
}