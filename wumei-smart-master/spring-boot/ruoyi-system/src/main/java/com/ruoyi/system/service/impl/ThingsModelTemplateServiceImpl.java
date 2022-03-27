package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.ThingsModelTemplate;
import com.ruoyi.system.mapper.ThingsModelTemplateMapper;
import com.ruoyi.system.service.IThingsModelTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ruoyi.common.utils.SecurityUtils.getLoginUser;

/**
 * 通用物模型Service业务层处理
 * 
 * @author kerwincui
 * @date 2021-12-16
 */
@Service
public class ThingsModelTemplateServiceImpl implements IThingsModelTemplateService
{
    @Autowired
    private ThingsModelTemplateMapper thingsModelTemplateMapper;

    /**
     * 查询通用物模型
     * 
     * @param templateId 通用物模型主键
     * @return 通用物模型
     */
    @Override
    public ThingsModelTemplate selectThingsModelTemplateByTemplateId(Long templateId)
    {
        return thingsModelTemplateMapper.selectThingsModelTemplateByTemplateId(templateId);
    }

    /**
     * 查询通用物模型列表
     * 
     * @param thingsModelTemplate 通用物模型
     * @return 通用物模型
     */
    @Override
    public List<ThingsModelTemplate> selectThingsModelTemplateList(ThingsModelTemplate thingsModelTemplate)
    {
        return thingsModelTemplateMapper.selectThingsModelTemplateList(thingsModelTemplate);
    }

    /**
     * 新增通用物模型
     * 
     * @param thingsModelTemplate 通用物模型
     * @return 结果
     */
    @Override
    public int insertThingsModelTemplate(ThingsModelTemplate thingsModelTemplate)
    {
        SysUser user = getLoginUser().getUser();
        List<SysRole> roles=user.getRoles();
        if(roles==null || roles.size()==0) {return 0;}
        // 系统管理员
        if(roles.stream().anyMatch(a->a.getRoleKey().equals("admin"))){
            thingsModelTemplate.setIsSys(1);
        }
        thingsModelTemplate.setTenantId(user.getUserId());
        thingsModelTemplate.setTenantName(user.getUserName());
        thingsModelTemplate.setCreateTime(DateUtils.getNowDate());
        return thingsModelTemplateMapper.insertThingsModelTemplate(thingsModelTemplate);
    }

    /**
     * 修改通用物模型
     * 
     * @param thingsModelTemplate 通用物模型
     * @return 结果
     */
    @Override
    public int updateThingsModelTemplate(ThingsModelTemplate thingsModelTemplate)
    {
        thingsModelTemplate.setUpdateTime(DateUtils.getNowDate());
        return thingsModelTemplateMapper.updateThingsModelTemplate(thingsModelTemplate);
    }

    /**
     * 批量删除通用物模型
     * 
     * @param templateIds 需要删除的通用物模型主键
     * @return 结果
     */
    @Override
    public int deleteThingsModelTemplateByTemplateIds(Long[] templateIds)
    {
        return thingsModelTemplateMapper.deleteThingsModelTemplateByTemplateIds(templateIds);
    }

    /**
     * 删除通用物模型信息
     * 
     * @param templateId 通用物模型主键
     * @return 结果
     */
    @Override
    public int deleteThingsModelTemplateByTemplateId(Long templateId)
    {
        return thingsModelTemplateMapper.deleteThingsModelTemplateByTemplateId(templateId);
    }
}
