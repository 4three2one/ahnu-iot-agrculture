package com.ruoyi.web.controller.system;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SysRegisterController extends BaseController {

    @Autowired
    private ISysUserService userService;


    @PostMapping("/register")
    @ApiOperation(value = "新增用户", notes = "新增用户")
    public AjaxResult add(@Validated @RequestBody SysUser user) {
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName()))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        } else if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setNickName("游客");
        Long[] roles = new Long[1];
        roles[0] = 2L;
        user.setRoleIds(roles);
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        return toAjax(userService.insertUser(user));
    }
    @PostMapping("/forgetpassword")
    @ApiOperation(value = "新增用户", notes = "新增用户")
    public AjaxResult forgetpassword(@Validated @RequestBody SysUser user) {
        if(userService.checkUserNameUnique(user.getUserName()) == "0") {
            return AjaxResult.error("修改密码'" + user.getUserName() + "'失败，账号不存在");
        }else if(userService.selectUserByUserName(user.getUserName()).getPhonenumber().equals(user.getPhonenumber()) == false ||userService.selectUserByUserName(user.getUserName()).getEmail().equals(user.getEmail()) == false){
            return AjaxResult.error("修改密码'" + user.getUserName() + "'失败，手机号或邮箱错误");
        }
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        return toAjax(userService.resetUserPwd(user.getUserName(),user.getPassword()));
    }
}
