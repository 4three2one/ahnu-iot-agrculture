package com.ruoyi.system.service.impl;

import java.util.List;
                                                                                                                                                                                            import com.ruoyi.common.utils.DateUtils;
            import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
    import com.ruoyi.system.mapper.IotThingsModelMapper;
import com.ruoyi.system.domain.IotThingsModel;
import com.ruoyi.system.service.IIotThingsModelService;

/**
 * 物模型模板Service业务层处理
 *
 * @author wxy
 * @date 2022-03-24
 */
@Service
public class IotThingsModelServiceImpl implements IIotThingsModelService {
    @Autowired
    private IotThingsModelMapper iotThingsModelMapper;

    /**
     * 查询物模型模板
     *
     * @param modelId 物模型模板ID
     * @return 物模型模板
     */
    @Override
    public IotThingsModel selectIotThingsModelById(Long modelId) {
        return iotThingsModelMapper.selectIotThingsModelById(modelId);
    }

    /**
     * 查询物模型模板列表
     *
     * @param iotThingsModel 物模型模板
     * @return 物模型模板
     */
    @Override
    public List<IotThingsModel> selectIotThingsModelList(IotThingsModel iotThingsModel) {
        return iotThingsModelMapper.selectIotThingsModelList(iotThingsModel);
    }

    /**
     * 新增物模型模板
     *
     * @param iotThingsModel 物模型模板
     * @return 结果
     */
            @Override
    public int insertIotThingsModel(IotThingsModel iotThingsModel) {
                                                                                                                                                                                                                                                                                                                            iotThingsModel.setCreateTime(DateUtils.getNowDate());
                                                                                                    return iotThingsModelMapper.insertIotThingsModel(iotThingsModel);
            }

    /**
     * 修改物模型模板
     *
     * @param iotThingsModel 物模型模板
     * @return 结果
     */
            @Override
    public int updateIotThingsModel(IotThingsModel iotThingsModel) {
                                                                                                                                                                                                                                                                                                                                                                    iotThingsModel.setUpdateTime(DateUtils.getNowDate());
                                                        return iotThingsModelMapper.updateIotThingsModel(iotThingsModel);
    }

    /**
     * 批量删除物模型模板
     *
     * @param modelIds 需要删除的物模型模板ID
     * @return 结果
     */
            @Override
    public int deleteIotThingsModelByIds(Long[] modelIds) {
                return iotThingsModelMapper.deleteIotThingsModelByIds(modelIds);
    }

    /**
     * 删除物模型模板信息
     *
     * @param modelId 物模型模板ID
     * @return 结果
     */
    @Override
    public int deleteIotThingsModelById(Long modelId) {
                return iotThingsModelMapper.deleteIotThingsModelById(modelId);
    }
    }
