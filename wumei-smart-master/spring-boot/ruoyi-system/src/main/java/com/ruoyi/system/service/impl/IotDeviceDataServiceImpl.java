package com.ruoyi.system.service.impl;

import java.util.List;
                                                                                            import com.ruoyi.common.utils.DateUtils;
            import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
    import com.ruoyi.system.mapper.IotDeviceDataMapper;
import com.ruoyi.system.domain.IotDeviceData;
import com.ruoyi.system.service.IIotDeviceDataService;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author wxy
 * @date 2022-03-24
 */
@Service
public class IotDeviceDataServiceImpl implements IIotDeviceDataService {
    @Autowired
    private IotDeviceDataMapper iotDeviceDataMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public IotDeviceData selectIotDeviceDataById(Long id) {
        return iotDeviceDataMapper.selectIotDeviceDataById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param iotDeviceData 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<IotDeviceData> selectIotDeviceDataList(IotDeviceData iotDeviceData) {
        return iotDeviceDataMapper.selectIotDeviceDataList(iotDeviceData);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param iotDeviceData 【请填写功能名称】
     * @return 结果
     */
            @Override
    public int insertIotDeviceData(IotDeviceData iotDeviceData) {
                                                                                                                                                            iotDeviceData.setCreateTime(DateUtils.getNowDate());
                                                            return iotDeviceDataMapper.insertIotDeviceData(iotDeviceData);
            }

    /**
     * 修改【请填写功能名称】
     *
     * @param iotDeviceData 【请填写功能名称】
     * @return 结果
     */
            @Override
    public int updateIotDeviceData(IotDeviceData iotDeviceData) {
                                                                                                                                                                                        return iotDeviceDataMapper.updateIotDeviceData(iotDeviceData);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】ID
     * @return 结果
     */
            @Override
    public int deleteIotDeviceDataByIds(Long[] ids) {
                return iotDeviceDataMapper.deleteIotDeviceDataByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteIotDeviceDataById(Long id) {
                return iotDeviceDataMapper.deleteIotDeviceDataById(id);
    }
    }
