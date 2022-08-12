package com.ruoyi.system.service.impl;

import java.util.List;
                                                                                                                    import com.ruoyi.common.utils.DateUtils;
            import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
    import com.ruoyi.system.mapper.IotImgRecogMapper;
import com.ruoyi.system.domain.IotImgRecog;
import com.ruoyi.system.service.IIotImgRecogService;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author wxy
 * @date 2022-08-10
 */
@Service
public class IotImgRecogServiceImpl implements IIotImgRecogService {
    @Autowired
    private IotImgRecogMapper iotImgRecogMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param imgId 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public IotImgRecog selectIotImgRecogById(Long imgId) {
        return iotImgRecogMapper.selectIotImgRecogById(imgId);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param iotImgRecog 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<IotImgRecog> selectIotImgRecogList(IotImgRecog iotImgRecog) {
        return iotImgRecogMapper.selectIotImgRecogList(iotImgRecog);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param iotImgRecog 【请填写功能名称】
     * @return 结果
     */
            @Override
    public int insertIotImgRecog(IotImgRecog iotImgRecog) {
                                                                                                                                                                                                    iotImgRecog.setCreateTime(DateUtils.getNowDate());
                                                                                return iotImgRecogMapper.insertIotImgRecog(iotImgRecog);
            }

    /**
     * 修改【请填写功能名称】
     *
     * @param iotImgRecog 【请填写功能名称】
     * @return 结果
     */
            @Override
    public int updateIotImgRecog(IotImgRecog iotImgRecog) {
                                                                                                                                                                                                                                            iotImgRecog.setUpdateTime(DateUtils.getNowDate());
                                    return iotImgRecogMapper.updateIotImgRecog(iotImgRecog);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param imgIds 需要删除的【请填写功能名称】ID
     * @return 结果
     */
            @Override
    public int deleteIotImgRecogByIds(Long[] imgIds) {
                return iotImgRecogMapper.deleteIotImgRecogByIds(imgIds);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param imgId 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteIotImgRecogById(Long imgId) {
                return iotImgRecogMapper.deleteIotImgRecogById(imgId);
    }
    }
