package com.ruoyi.system.service.impl;

import java.util.List;
                                                        import com.ruoyi.common.utils.DateUtils;
            import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
    import com.ruoyi.system.mapper.IotDeviceModelMapper;
import com.ruoyi.system.domain.IotDeviceModel;
import com.ruoyi.system.service.IIotDeviceModelService;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author wxy
 * @date 2022-03-27
 */
@Service
public class IotDeviceModelServiceImpl implements IIotDeviceModelService {
    @Autowired
    private IotDeviceModelMapper iotDeviceModelMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param deviceModelId 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public IotDeviceModel selectIotDeviceModelById(Long deviceModelId) {
        return iotDeviceModelMapper.selectIotDeviceModelById(deviceModelId);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param iotDeviceModel 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<IotDeviceModel> selectIotDeviceModelList(IotDeviceModel iotDeviceModel) {
        return iotDeviceModelMapper.selectIotDeviceModelList(iotDeviceModel);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param iotDeviceModel 【请填写功能名称】
     * @return 结果
     */
            @Override
    public int insertIotDeviceModel(IotDeviceModel iotDeviceModel) {
                                                                                                iotDeviceModel.setCreateTime(DateUtils.getNowDate());
                                        return iotDeviceModelMapper.insertIotDeviceModel(iotDeviceModel);
            }

    /**
     * 修改【请填写功能名称】
     *
     * @param iotDeviceModel 【请填写功能名称】
     * @return 结果
     */
            @Override
    public int updateIotDeviceModel(IotDeviceModel iotDeviceModel) {
                                                                                                        return iotDeviceModelMapper.updateIotDeviceModel(iotDeviceModel);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param deviceModelIds 需要删除的【请填写功能名称】ID
     * @return 结果
     */
            @Override
    public int deleteIotDeviceModelByIds(Long[] deviceModelIds) {
                return iotDeviceModelMapper.deleteIotDeviceModelByIds(deviceModelIds);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param deviceModelId 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteIotDeviceModelById(Long deviceModelId) {
                return iotDeviceModelMapper.deleteIotDeviceModelById(deviceModelId);
    }
    }
