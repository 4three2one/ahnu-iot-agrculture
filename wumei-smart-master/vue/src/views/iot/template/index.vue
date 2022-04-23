<template>
<div style="padding:6px;">
    <el-card style="padding-bottom:100px;">
        <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['iot:template:add']">新增</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['iot:template:edit']">修改</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['iot:template:remove']">删除</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" v-hasPermi="['iot:template:export']">导出</el-button>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="templateList" @selection-change="handleSelectionChange" border>
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="名称" align="center" prop="templateName" />
            <el-table-column label="标识符" align="center" prop="identifier" />
            <el-table-column label="创建时间" align="center" prop="createTime" width="180">
                <template slot-scope="scope">
                    <span>{{ parseTime(scope.row.createTime, "{y}-{m}-{d}") }}</span>
                </template>
            </el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="150">
                <template slot-scope="scope">
                    <el-button size="small" type="primary" style="padding:5px;" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['iot:template:edit']">修改</el-button>
                    <el-button size="small" type="danger" style="padding:5px;" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['iot:template:remove']">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
        <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
            <el-form ref="form" :model="form" :rules="rules" label-width="100px">
                <el-form-item label="名称" prop="templateName">
                    <el-input v-model="form.templateName" placeholder="请输入物模型名称，例如：温度" />
                </el-form-item>
                <el-form-item label="标识符" prop="identifier">
                    <el-input v-model="form.identifier" placeholder="请输入标识符，例如：temperature" />
                </el-form-item>
                <el-divider></el-divider>
                <el-form-item label="数据类型" prop="datatype">
                    <el-select v-model="form.datatype" placeholder="请选择数据类型" @change="dataTypeChange">
                        <el-option key="integer" label="整数" value="integer"></el-option>
                        <el-option key="decimal" label="小数" value="decimal"></el-option>
<!--                        <el-option key="bool" label="布尔" value="bool" :disabled="form.isMonitor==1"></el-option>-->
<!--                        <el-option key="enum" label="枚举" value="enum" :disabled="form.isMonitor==1"></el-option>-->
<!--                        <el-option key="string" label="字符串" value="string" :disabled="form.isMonitor==1"></el-option>-->
<!--                        <el-option key="array" label="数组" value="array" :disabled="form.isMonitor==1"></el-option>-->
                    </el-select>
                </el-form-item>
                <div v-if="form.datatype == 'integer' || form.datatype == 'decimal'">
                    <el-form-item label="取值范围">
                        <el-row>
                            <el-col :span="9">
                                <el-input v-model="form.specs.min" placeholder="最小值" type="number" />
                            </el-col>
                            <el-col :span="2" align="center">到</el-col>
                            <el-col :span="9">
                                <el-input v-model="form.specs.max" placeholder="最大值" type="number" />
                            </el-col>
                        </el-row>
                    </el-form-item>
                    <el-form-item label="单位">
                      <el-col :span="9">
                        <el-input v-model="form.specs.unit" placeholder="请输入单位，例如：℃" />
                      </el-col>
                    </el-form-item>
                  <el-divider></el-divider>
                  <el-form-item label="AT指令">
                    <el-col :span="12">
                      <el-input v-model="form.specs.atcmd" placeholder="请输入AT指令" />
                    </el-col>
                  </el-form-item>
                  <el-form-item label="数据索引">
                      <el-input-number v-model="form.specs.index" :min="0"  type="number"/>
                  </el-form-item>
<!--                  <el-form-item label="cron表达式"  >-->
<!--                    <el-col :span="12">-->
<!--                      <el-input v-model="form.specs.cron" placeholder="请输入cron执行表达式" />-->
<!--                    </el-col>-->
<!--                  </el-form-item>-->
                  <el-form-item label="状态">
                    <el-col :span="8">
                      <el-radio-group v-model="form.specs.status">
                        <el-radio
                          v-for="dict in statusOptions"
                          :key="dict.dictValue"
                          :label="dict.dictValue"
                        >{{dict.dictLabel}}</el-radio>
                      </el-radio-group>
                    </el-col>
                  </el-form-item>
                </div>
            </el-form>

            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitForm">确 定</el-button>
                <el-button @click="cancel">取 消</el-button>
            </div>
        </el-dialog>

    </el-card>
</div>
</template>

<style>
.specsColor {
    background-color: #fcfcfc;
}
</style>

<script>
import {
    listTemplate,
    getTemplate,
    delTemplate,
    addTemplate,
    updateTemplate,
} from "@/api/iot/template";
export default {
    name: "Template",
    data() {
        return {
            // 遮罩层
            loading: true,
            // 选中数组
            ids: [],
            // 非单个禁用
            single: true,
            // 非多个禁用
            multiple: true,
            // 显示搜索条件
            showSearch: true,
            // 总条数
            total: 0,
            // 通用物模型表格数据
            templateList: [],
            // 弹出层标题
            title: "",
            // 是否显示弹出层
            open: false,
            // 查询参数
            queryParams: {
                pageNum: 1,
                pageSize: 10,
                templateName: null,
                type: null,
            },
            thingsOptions: [],
            dataOptions: [],
            // 表单参数
            form: {},
            //物模型任务状态字典
            statusOptions: [],
            // 表单校验
            rules: {
                templateName: [{
                    required: true,
                    message: "物模型名称不能为空",
                    trigger: "blur"
                }, ],
                identifier: [{
                    required: true,
                    message: "标识符，产品下唯一不能为空",
                    trigger: "blur",
                }, ],
                type: [{
                    required: true,
                    message: "模型类别不能为空",
                    trigger: "change"
                }, ],
                datatype: [{
                    required: true,
                    message: "数据类型不能为空",
                    trigger: "change"
                }, ],
                specs: [{
                    required: true,
                    message: "数据定义不能为空",
                    trigger: "blur"
                }, ],
            },
        };
    },
    created() {
        this.getList();
        this.getDicts("iot_things_type").then(response => {
          this.thingsOptions = response.data;
        });
        this.getDicts("iot_data_type").then(response => {
          this.dataOptions = response.data;
        });
        this.getDicts("sys_job_status").then(response => {
          this.statusOptions = response.data;
        });
    },
    methods: {
        /** 查询通用物模型列表 */
        getList() {
            this.loading = true;
            listTemplate(this.queryParams).then((response) => {
                this.templateList = response.rows;
                this.total = response.total;
                this.loading = false;
            });
        },
        // 取消按钮
        cancel() {
            this.open = false;
            this.reset();
        },
        // 表单重置
        reset() {
            this.form = {
                templateId: null,
                templateName: null,
                userId: null,
                userName: null,
                tenantId: null,
                tenantName: null,
                identifier: null,
                type: null,
                datatype: null,
                isSys: null,
                isTop: null,
                isMonitor: null,
                delFlag: null,
                createBy: null,
                createTime: null,
                updateBy: null,
                updateTime: null,
                remark: null,
                specs: null,
            };
            this.resetForm("form");
        },
        // 状态字典翻译
        statusFormat(row, column) {
          return this.selectDictLabel(this.statusOptions, row.status);
        },
        /** 搜索按钮操作 */
        handleQuery() {
            this.queryParams.pageNum = 1;
            this.getList();
        },
        /** 重置按钮操作 */
        resetQuery() {
            this.resetForm("queryForm");
            this.handleQuery();
        },
        // 多选框选中数据
        handleSelectionChange(selection) {
            this.ids = selection.map((item) => item.templateId);
            this.single = selection.length !== 1;
            this.multiple = !selection.length;
        },
        /** 新增按钮操作 */
        handleAdd() {
            this.reset();
            this.open = true;
            this.title = "添加通用物模型";
            this.form.type = 1;
            this.form.datatype = "integer"
            this.form.specs = {
                enumList: [],
            };
        },
        /** 修改按钮操作 */
        handleUpdate(row) {
            this.reset();
            const templateId = row.templateId || this.ids;
            getTemplate(templateId).then((response) => {
                this.form = response.data;
                this.open = true;
                this.title = "修改通用物模型";
                // Json转对象
                this.form.specs = JSON.parse(this.form.specs);
            });
        },
        /** 提交按钮 */
        submitForm() {
            this.$refs["form"].validate((valid) => {
                if (valid) {
                    if (this.form.templateId != null) {
                        // 格式化specs
                        this.form.specs = this.formatThingsSpecs();
                        // if (this.form.type == 2) {
                        //     this.form.isMonitor = 0;
                        // } else if (this.form.type == 3) {
                        //     this.form.isMonitor = 0;
                        //     this.form.isTop = 0;
                        // }
                        updateTemplate(this.form).then((response) => {
                            this.msgSuccess("修改成功");
                            this.open = false;
                            this.getList();
                        });
                    } else {
                        // 格式化specs
                        this.form.specs = this.formatThingsSpecs();
                        // if (this.form.type == 2) {
                        //     this.form.isMonitor = 0;
                        // } else if (this.form.type == 3) {
                        //     this.form.isMonitor = 0;
                        //     this.form.isTop = 0;
                        // }
                        addTemplate(this.form).then((response) => {
                            this.msgSuccess("新增成功");
                            this.open = false;
                            this.getList();
                        });
                    }
                }
            });
        },
        /** 删除按钮操作 */
        handleDelete(row) {
            const templateIds = row.templateId || this.ids;
            this.$confirm('是否确认删除通用物模型编号为"' + templateIds + '"的数据项？')
                .then(function () {
                    return delTemplate(templateIds);
                })
                .then(() => {
                    this.getList();
                    this.msgSuccess("删除成功");
                })
                .catch(() => {});
        },
        /** 导出按钮操作 */
        handleExport() {
            this.download(
                "iot/template/export", {
                    ...this.queryParams,
                },
                `template_${new Date().getTime()}.xlsx`
            );
        },
        // 类型改变
        typeChange(label){
            if(label==2 || label==3){
                this.form.isMonitor=0;
            }
        },
        // 实时监测改变
        changeMonitor(isMonitor){
            if(isMonitor==1 && this.form.datatype!="integer" && this.form.datatype!="decimal"){
                this.form.datatype="integer";
            }
        },
        // 格式化物模型
        formatThingsSpecs() {
            var data = {};
            data.type = this.form.datatype;
            if (this.form.datatype == "integer" || this.form.datatype == "decimal") {
                data.min = Number(this.form.specs.min);
                data.max = Number(this.form.specs.max);
                data.unit = this.form.specs.unit;
                data.step = Number(this.form.specs.step);
            } else if (this.form.datatype == "string") {
                data.maxLength = Number(this.form.specs.maxLength);
            } else if (this.form.datatype == "bool") {
                data.falseText = this.form.specs.falseText;
                data.trueText = this.form.specs.trueText;
            } else if (this.form.datatype == "array") {
                data.arrayType = this.form.specs.arrayType;
            } else if (this.form.datatype == "enum") {
                data.enumList = this.form.specs.enumList;
            }
            data.atcmd=this.form.specs.atcmd;
            data.index=this.form.specs.index;
            // data.cron=this.form.specs.cron;
            data.status=this.form.specs.status;
            return JSON.stringify(data);
        },
        /** 切换为枚举项 */
        dataTypeChange(val) {
            if (val == "enum") {
                this.form.specs.enumList = [{
                    value: "",
                    text: ""
                }];
            } else if (val == "array") {
                this.form.specs.arrayType = "int";
            }
        },
        /** 添加枚举项 */
        addEnumItem() {
            this.form.specs.enumList.push({
                value: "",
                text: ""
            })
        },
        /** 删除枚举项 */
        removeEnumItem(index) {
            this.form.specs.enumList.splice(index, 1);
        },
        /** 格式化显示数据定义 */
        formatSpecsDisplay(json) {
            let specs = JSON.parse(json);
            if (specs.type === "integer" || specs.type === "decimal") {
                return "<span style='width:50%;display:inline-block;'>最大值：<span style=\"color:#f56c6c\">" + specs.max +
                    "</span></span>最小值：<span style=\"color:#F56C6C\">" + specs.min +
                    "</span><br /><span style='width:50%;display:inline-block;'>步长：<span style=\"color:#F56C6C\">" + specs.step +
                    "</span></span>单位：<span style=\"color:#F56C6C\">" + specs.unit;
            } else if (specs.type === "string") {
                return "最大长度：<span style=\"color:#F56C6C\">" + specs.maxLength + "</span>";
            } else if (specs.type === "array") {
                return "数组类型：<span style=\"color:#F56C6C\">" + specs.arrayType + "</span>";
            } else if (specs.type === "enum") {
                let items = "";
                for (let i = 0; i < specs.enumList.length; i++) {
                    items = items + "<span style='width:50%;display:inline-block;'>" + specs.enumList[i].value + "：<span style='color:#F56C6C'>" + specs.enumList[i].text + "</span></span>"
                    if (i > 0 && i % 2 != 0) {
                        items = items + "<br />"
                    }
                }
                return items;
            } else if (specs.type === "bool") {
                return "<span style='width:50%;display:inline-block;'>0：<span style=\"color:#F56C6C\">" + specs.falseText +
                    "</span></span>1：<span style=\"color:#F56C6C\">" + specs.trueText
            }
        },

    },
};
</script>
