package com.poetry.admin.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.poetry.admin.dao.SysUserInfoDAO;
import com.poetry.admin.entity.SysUserInfo;
import com.poetry.admin.service.SysUserInfoService;
import org.springframework.stereotype.Service;

/**
 * 用户表 Service 实现类
 * 
 * @author lkl
 * @version 2021-04-23
 */
@Service
public class SysUserInfoServiceImpl extends ServiceImpl<SysUserInfoDAO,SysUserInfo> implements SysUserInfoService {

}