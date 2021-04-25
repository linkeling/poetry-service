package com.poetry.admin.biz.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.poetry.admin.BaseBizServiceImpl;
import com.poetry.admin.biz.SysUserInfoBizService;
import com.poetry.admin.exception.BusinessException;
import com.poetry.admin.param.output.SysUserInfoDTO;
import com.poetry.admin.entity.SysUserInfo;
import com.poetry.admin.param.query.SysUserInfoQueryVO;
import com.poetry.admin.param.save.SysUserInfoSaveVO;
import com.poetry.admin.service.SysUserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * 用户表 业务Service 实现类
 * 
 * @author lkl
 * @version 2021-04-23
 */
@Service
public class SysUserInfoBizServiceImpl extends BaseBizServiceImpl<SysUserInfoSaveVO, SysUserInfoQueryVO, SysUserInfoDTO, SysUserInfo, SysUserInfoService> implements SysUserInfoBizService {
    @Override
    protected void setCriteriaForQuery(SysUserInfoQueryVO vo, QueryWrapper<SysUserInfo> criteria) {
        if(Objects.nonNull(vo.getUserName())){
            criteria.eq("user_name", vo.getUserName());
        }
        if(Objects.nonNull(vo.getPassword())){
            criteria.eq("password", vo.getPassword());
        }
        if(Objects.nonNull(vo.getLastLoginTime())){
            criteria.eq("last_login_time", vo.getLastLoginTime());
        }
        if(Objects.nonNull(vo.getLastLoginIp())){
            criteria.eq("last_login_ip", vo.getLastLoginIp());
        }
        if(Objects.nonNull(vo.getUserType())){
            criteria.eq("user_type", vo.getUserType());
        }
        if(Objects.nonNull(vo.getRelationId())){
            criteria.eq("relation_id", vo.getRelationId());
        }
        if(Objects.nonNull(vo.getRoleId())){
            criteria.eq("role_id", vo.getRoleId());
        }
        if(Objects.nonNull(vo.getName())){
            criteria.eq("name", vo.getName());
        }
        if(Objects.nonNull(vo.getContactsPhone())){
            criteria.eq("contacts_phone", vo.getContactsPhone());
        }
        if(Objects.nonNull(vo.getCreateUser())){
            criteria.eq("create_user", vo.getCreateUser());
        }
        if(Objects.nonNull(vo.getValid())){
            criteria.eq("valid", vo.getValid());
        }
        if(Objects.nonNull(vo.getCreated())){
            criteria.eq("created", vo.getCreated());
        }
        if(Objects.nonNull(vo.getUpdated())){
            criteria.eq("updated", vo.getUpdated());
        }
    }

    @Override
    public SysUserInfoDTO userLogin(String userName, String password) {
        SysUserInfoQueryVO vo=new SysUserInfoQueryVO();
        vo.setUserName(userName);
        //查询有无用户
        List<SysUserInfoDTO> userInfoList=super.queryList(vo);
        if (CollectionUtils.isEmpty(userInfoList)){
            throw new BusinessException("当前登录用户名不存在！");
        }
        if (userInfoList.size()>1){
            throw new BusinessException("当前用户名重复，请联系管理员处理！");
        }
        SysUserInfoDTO sysUserInfoDTO=userInfoList.get(0);
        if (!sysUserInfoDTO.getPassword().equals(password)){
            throw new BusinessException("密码不一致，请输入正确的用户名！");
        }
        return sysUserInfoDTO;
    }
}