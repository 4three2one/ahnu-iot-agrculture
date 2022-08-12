package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.system.domain.IotImgRecog;

/**
 * 【请填写功能名称】Service接口
 *
 * @author wxy
 * @date 2022-08-10
 */
public interface IIotImgRecogService {
    /**
     * 查询【请填写功能名称】
     *
     * @param imgId 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public IotImgRecog selectIotImgRecogById(Long imgId);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param iotImgRecog 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<IotImgRecog> selectIotImgRecogList(IotImgRecog iotImgRecog);

    /**
     * 新增【请填写功能名称】
     *
     * @param iotImgRecog 【请填写功能名称】
     * @return 结果
     */
    public int insertIotImgRecog(IotImgRecog iotImgRecog);

    /**
     * 修改【请填写功能名称】
     *
     * @param iotImgRecog 【请填写功能名称】
     * @return 结果
     */
    public int updateIotImgRecog(IotImgRecog iotImgRecog);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param imgIds 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    public int deleteIotImgRecogByIds(Long[] imgIds);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param imgId 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteIotImgRecogById(Long imgId);
}
