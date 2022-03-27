package com.ruoyi.system.mapper;

import java.util.List;

import com.ruoyi.system.domain.IotDeviceModel;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author wxy
 * @date 2022-03-27
 */
public interface IotDeviceModelMapper {
    /**
     * 查询【请填写功能名称】
     *
     * @param deviceModelId 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public IotDeviceModel selectIotDeviceModelById(Long deviceModelId);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param iotDeviceModel 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<IotDeviceModel> selectIotDeviceModelList(IotDeviceModel iotDeviceModel);

    /**
     * 新增【请填写功能名称】
     *
     * @param iotDeviceModel 【请填写功能名称】
     * @return 结果
     */
    public int insertIotDeviceModel(IotDeviceModel iotDeviceModel);

    /**
     * 修改【请填写功能名称】
     *
     * @param iotDeviceModel 【请填写功能名称】
     * @return 结果
     */
    public int updateIotDeviceModel(IotDeviceModel iotDeviceModel);

    /**
     * 删除【请填写功能名称】
     *
     * @param deviceModelId 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteIotDeviceModelById(Long deviceModelId);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param deviceModelIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteIotDeviceModelByIds(Long[] deviceModelIds);
    }
