package com.poetry.admin.biz;
import com.poetry.admin.base.BaseService;
import com.poetry.admin.param.output.SysUserInfoDTO;
import com.poetry.admin.param.query.SysUserInfoQueryVO;
import com.poetry.admin.param.save.SysUserInfoSaveVO;

/**
 * 用户表 业务Service
 * 
 * @author lkl
 * @version 2021-04-23
 */
public interface SysUserInfoBizService extends BaseService<SysUserInfoSaveVO, SysUserInfoQueryVO, SysUserInfoDTO> {

    /**
     * 获取登录信息
     * @param userName
     * @param password
     * @return
     */
    SysUserInfoDTO userLogin(String userName, String password);
}