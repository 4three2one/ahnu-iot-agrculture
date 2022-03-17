package com.ruoyi.system.mapper;

import java.util.List;

import com.ruoyi.system.domain.IotDeviceStatus;
import org.springframework.stereotype.Repository;

/**
 * 设备状态Mapper接口
 *
 * @author kerwincui
 * @date 2021-05-06
 */
@Repository
public interface IotDeviceStatusMapper {
    /**
     * 查询设备状态
     *
     * @param deviceStatusId 设备状态ID
     * @return 设备状态
     */
    public IotDeviceStatus selectIotDeviceStatusById(Long deviceStatusId);

    /**
     * 查询设备最新状态
     *
     * @param deviceId 设备ID
     * @return 设备状态
     */
    public IotDeviceStatus selectIotDeviceStatusByDeviceId(Long deviceId);

    /**
     * 根据设备编号查询设备最新状态
     *
     * @param deviceNum 设备编号
     * @return 设备状态
     */
    public IotDeviceStatus selectIotDeviceStatusByDeviceNum(String deviceNum);

    /**
     * 查询设备状态列表
     *
     * @param iotDeviceStatus 设备状态
     * @return 设备状态集合
     */
    public List<IotDeviceStatus> selectIotDeviceStatusList(IotDeviceStatus iotDeviceStatus);

    /**
     * 新增设备状态
     *
     * @param iotDeviceStatus 设备状态
     * @return 结果
     */
    public int insertIotDeviceStatus(IotDeviceStatus iotDeviceStatus);

    /**
     * 修改设备状态
     *
     * @param iotDeviceStatus 设备状态
     * @return 结果
     */
    public int updateIotDeviceStatus(IotDeviceStatus iotDeviceStatus);

    /**
     * 删除设备状态
     *
     * @param deviceStatusId 设备状态ID
     * @return 结果
     */
    public int deleteIotDeviceStatusById(Long deviceStatusId);

    /**
     * 批量删除设备状态
     *
     * @param deviceStatusIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteIotDeviceStatusByIds(Long[] deviceStatusIds);
}
