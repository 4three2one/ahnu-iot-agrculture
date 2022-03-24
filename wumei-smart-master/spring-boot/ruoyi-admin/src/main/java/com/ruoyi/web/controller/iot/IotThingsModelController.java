package com.ruoyi.web.controller.iot;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.IotThingsModel;
import com.ruoyi.system.service.IIotThingsModelService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 物模型模板Controller
 *
 * @author wxy
 * @date 2022-03-24
 */
@RestController
@RequestMapping("/system/model")
public class IotThingsModelController extends BaseController {
    @Autowired
    private IIotThingsModelService iotThingsModelService;

/**
 * 查询物模型模板列表
 */
@PreAuthorize("@ss.hasPermi('system:model:list')")
@GetMapping("/list")
        public TableDataInfo list(IotThingsModel iotThingsModel) {
        startPage();
        List<IotThingsModel> list = iotThingsModelService.selectIotThingsModelList(iotThingsModel);
        return getDataTable(list);
    }
    
    /**
     * 导出物模型模板列表
     */
    @PreAuthorize("@ss.hasPermi('system:model:export')")
    @Log(title = "物模型模板", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(IotThingsModel iotThingsModel) {
        List<IotThingsModel> list = iotThingsModelService.selectIotThingsModelList(iotThingsModel);
        ExcelUtil<IotThingsModel> util = new ExcelUtil<IotThingsModel>(IotThingsModel. class);
        return util.exportExcel(list, "model");
    }

    /**
     * 获取物模型模板详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:model:query')")
    @GetMapping(value = "/{modelId}")
    public AjaxResult getInfo(@PathVariable("modelId") Long modelId) {
        return AjaxResult.success(iotThingsModelService.selectIotThingsModelById(modelId));
    }

    /**
     * 新增物模型模板
     */
    @PreAuthorize("@ss.hasPermi('system:model:add')")
    @Log(title = "物模型模板", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody IotThingsModel iotThingsModel) {
        return toAjax(iotThingsModelService.insertIotThingsModel(iotThingsModel));
    }

    /**
     * 修改物模型模板
     */
    @PreAuthorize("@ss.hasPermi('system:model:edit')")
    @Log(title = "物模型模板", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody IotThingsModel iotThingsModel) {
        return toAjax(iotThingsModelService.updateIotThingsModel(iotThingsModel));
    }

    /**
     * 删除物模型模板
     */
    @PreAuthorize("@ss.hasPermi('system:model:remove')")
    @Log(title = "物模型模板", businessType = BusinessType.DELETE)
    @DeleteMapping("/{modelIds}")
    public AjaxResult remove(@PathVariable Long[] modelIds) {
        return toAjax(iotThingsModelService.deleteIotThingsModelByIds(modelIds));
    }
}
