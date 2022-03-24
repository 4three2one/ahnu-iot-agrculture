package com.ruoyi.system.mapper;

import java.util.List;

import com.ruoyi.system.domain.IotDeviceData;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author wxy
 * @date 2022-03-24
 */
public interface IotDeviceDataMapper {
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public IotDeviceData selectIotDeviceDataById(Long id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param iotDeviceData 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<IotDeviceData> selectIotDeviceDataList(IotDeviceData iotDeviceData);

    /**
     * 新增【请填写功能名称】
     *
     * @param iotDeviceData 【请填写功能名称】
     * @return 结果
     */
    public int insertIotDeviceData(IotDeviceData iotDeviceData);

    /**
     * 修改【请填写功能名称】
     *
     * @param iotDeviceData 【请填写功能名称】
     * @return 结果
     */
    public int updateIotDeviceData(IotDeviceData iotDeviceData);

    /**
     * 删除【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteIotDeviceDataById(Long id);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteIotDeviceDataByIds(Long[] ids);
    }
