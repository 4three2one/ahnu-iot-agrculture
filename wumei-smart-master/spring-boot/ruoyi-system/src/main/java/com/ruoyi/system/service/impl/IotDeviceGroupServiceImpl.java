package com.ruoyi.system.service.impl;

import java.util.List;
                                                        import com.ruoyi.common.utils.DateUtils;
            import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
    import com.ruoyi.system.mapper.IotDeviceGroupMapper;
import com.ruoyi.system.domain.IotDeviceGroup;
import com.ruoyi.system.service.IIotDeviceGroupService;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author wxy
 * @date 2022-03-24
 */
@Service
public class IotDeviceGroupServiceImpl implements IIotDeviceGroupService {
    @Autowired
    private IotDeviceGroupMapper iotDeviceGroupMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param deviceGroupId 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public IotDeviceGroup selectIotDeviceGroupById(Long deviceGroupId) {
        return iotDeviceGroupMapper.selectIotDeviceGroupById(deviceGroupId);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param iotDeviceGroup 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<IotDeviceGroup> selectIotDeviceGroupList(IotDeviceGroup iotDeviceGroup) {
        return iotDeviceGroupMapper.selectIotDeviceGroupList(iotDeviceGroup);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param iotDeviceGroup 【请填写功能名称】
     * @return 结果
     */
            @Override
    public int insertIotDeviceGroup(IotDeviceGroup iotDeviceGroup) {
                                                                                                iotDeviceGroup.setCreateTime(DateUtils.getNowDate());
                                        return iotDeviceGroupMapper.insertIotDeviceGroup(iotDeviceGroup);
            }

    /**
     * 修改【请填写功能名称】
     *
     * @param iotDeviceGroup 【请填写功能名称】
     * @return 结果
     */
            @Override
    public int updateIotDeviceGroup(IotDeviceGroup iotDeviceGroup) {
                                                                                                        return iotDeviceGroupMapper.updateIotDeviceGroup(iotDeviceGroup);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param deviceGroupIds 需要删除的【请填写功能名称】ID
     * @return 结果
     */
            @Override
    public int deleteIotDeviceGroupByIds(Long[] deviceGroupIds) {
                return iotDeviceGroupMapper.deleteIotDeviceGroupByIds(deviceGroupIds);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param deviceGroupId 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteIotDeviceGroupById(Long deviceGroupId) {
                return iotDeviceGroupMapper.deleteIotDeviceGroupById(deviceGroupId);
    }
    }
