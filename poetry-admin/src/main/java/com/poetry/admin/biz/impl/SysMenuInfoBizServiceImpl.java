package com.poetry.admin.biz.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.poetry.admin.BaseBizServiceImpl;
import com.poetry.admin.biz.SysMenuInfoBizService;
import com.poetry.admin.param.output.SysMenuInfoDTO;
import com.poetry.admin.entity.SysMenuInfo;
import com.poetry.admin.param.query.SysMenuInfoQueryVO;
import com.poetry.admin.param.save.SysMenuInfoSaveVO;
import com.poetry.admin.service.SysMenuInfoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 系统管理-权限资源表  业务Service 实现类
 * 
 * @author lkl
 * @version 2021-04-23
 */
@Service
public class SysMenuInfoBizServiceImpl extends BaseBizServiceImpl<SysMenuInfoSaveVO, SysMenuInfoQueryVO, SysMenuInfoDTO, SysMenuInfo, SysMenuInfoService> implements SysMenuInfoBizService {
    @Override
    protected void setCriteriaForQuery(SysMenuInfoQueryVO vo, QueryWrapper<SysMenuInfo> criteria) {
        if(Objects.nonNull(vo.getParentId())){
            criteria.eq("parent_id", vo.getParentId());
        }
        if(Objects.nonNull(vo.getFrontUrl())){
            criteria.eq("front_url", vo.getFrontUrl());
        }
        if(Objects.nonNull(vo.getResources())){
            criteria.eq("resources", vo.getResources());
        }
        if(Objects.nonNull(vo.getMenuTitle())){
            criteria.eq("menu_title", vo.getMenuTitle());
        }
        if(Objects.nonNull(vo.getLevel())){
            criteria.eq("level", vo.getLevel());
        }
        if(Objects.nonNull(vo.getSortNo())){
            criteria.eq("sort_no", vo.getSortNo());
        }
        if(Objects.nonNull(vo.getRemarks())){
            criteria.eq("remarks", vo.getRemarks());
        }
        if(Objects.nonNull(vo.getCreated())){
            criteria.eq("created", vo.getCreated());
        }
        if(Objects.nonNull(vo.getUpdated())){
            criteria.eq("updated", vo.getUpdated());
        }
    }

    @Override
    public List<SysMenuInfoDTO> queryAll() {
        List<SysMenuInfoDTO> dtoList=super.queryAll();
        return createTree(0L,dtoList);
    }


    private List<SysMenuInfoDTO> createTree(Long pid, List<SysMenuInfoDTO> menus) {
        List<SysMenuInfoDTO> treeMenu = new ArrayList<>();
        for (SysMenuInfoDTO menu : menus) {
            if (pid.equals(menu.getParentId()) ) {
                treeMenu.add(menu);
                menu.setChildList(createTree(menu.getId(), menus));
            }
        }
        return treeMenu;
    }

}