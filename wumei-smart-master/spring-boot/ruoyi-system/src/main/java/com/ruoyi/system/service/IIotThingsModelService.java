package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.system.domain.IotThingsModel;

/**
 * 物模型模板Service接口
 *
 * @author wxy
 * @date 2022-03-24
 */
public interface IIotThingsModelService {
    /**
     * 查询物模型模板
     *
     * @param modelId 物模型模板ID
     * @return 物模型模板
     */
    public IotThingsModel selectIotThingsModelById(Long modelId);

    /**
     * 查询物模型模板列表
     *
     * @param iotThingsModel 物模型模板
     * @return 物模型模板集合
     */
    public List<IotThingsModel> selectIotThingsModelList(IotThingsModel iotThingsModel);

    /**
     * 新增物模型模板
     *
     * @param iotThingsModel 物模型模板
     * @return 结果
     */
    public int insertIotThingsModel(IotThingsModel iotThingsModel);

    /**
     * 修改物模型模板
     *
     * @param iotThingsModel 物模型模板
     * @return 结果
     */
    public int updateIotThingsModel(IotThingsModel iotThingsModel);

    /**
     * 批量删除物模型模板
     *
     * @param modelIds 需要删除的物模型模板ID
     * @return 结果
     */
    public int deleteIotThingsModelByIds(Long[] modelIds);

    /**
     * 删除物模型模板信息
     *
     * @param modelId 物模型模板ID
     * @return 结果
     */
    public int deleteIotThingsModelById(Long modelId);
}
