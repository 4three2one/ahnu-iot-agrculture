<template>
  <div class="app-container">
<!--    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设备ID" prop="deviceId">
        <el-input
          v-model="queryParams.deviceId"
          placeholder="请输入设备ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="图片名称" prop="imgName">
        <el-input
          v-model="queryParams.imgName"
          placeholder="请输入图片名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="疾病" prop="diseaseStatus">
        <el-select v-model="queryParams.diseaseStatus" placeholder="请选择疾病" clearable
                   size="small">
          <el-option label="请选择字典生成" value=""/>
        </el-select>
      </el-form-item>
      <el-form-item label="原始图片路径" prop="imgOrginPath">
        <el-input
          v-model="queryParams.imgOrginPath"
          placeholder="请输入原始图片路径"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="识别结果路径" prop="imgResultPath">
        <el-input
          v-model="queryParams.imgResultPath"
          placeholder="请输入识别结果路径"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable
                   size="small">
          <el-option label="请选择字典生成" value=""/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:recog:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:recog:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:recog:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:recog:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>-->

<!--    <el-table v-loading="loading" :data="recogList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="图片id" align="center" prop="imgId"/>
      <el-table-column label="设备ID" align="center" prop="deviceId"/>
      <el-table-column label="图片名称" align="center" prop="imgName"/>
      <el-table-column label="疾病" align="center" prop="diseaseStatus"/>
      <el-table-column label="原始图片路径" align="center" prop="imgOrginPath"/>
      <el-table-column label="识别结果路径" align="center" prop="imgResultPath"/>
      <el-table-column label="状态" align="center" prop="status"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:recog:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:recog:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>-->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:recog:add']"
        >新增
        </el-button>
      </el-col>
    </el-row>
    <el-card style="padding-bottom:100px;">
      <el-row :gutter="50" v-loading="loading">
        <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="12" v-for="(item,index) in recogList" :key="index" style="margin-bottom:30px;text-align:center;">
          <el-card :body-style="{ padding: '20px'}" shadow="always" class="card-item">
            <el-row  :gutter="20" >
              <el-col :span="12" >
                <el-tag>原始图片</el-tag>
              </el-col>
              <el-col :span="12" >
                <el-tag type="success">结果图片</el-tag>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="12" >
                <div style="margin-top:10px;">
                  <el-image  style="width:256px;height:256px;"  :src="baseUrl+item.imgOrginPath" fit="cover" ></el-image>
<!--                  <el-image  style="width:256px;height:256px;" :preview-src-list="[require('@/assets/images/tomato.jpg')]" :src="require('@/assets/images/tomato.jpg')" fit="cover" ></el-image>-->
                </div>
              </el-col>
              <el-col :span="12" >
                <div style="margin-top:10px;">
                  <el-image style="width:256px;height:256px;"  :src="baseUrl+item.imgResultPath" fit="cover"></el-image>
<!--                  <el-image style="width:256px;height:256px;" :preview-src-list="[require('@/assets/images/tomato.jpg')]" :src="require('@/assets/images/tomato.jpg')" fit="cover" ></el-image>-->
                </div>
              </el-col>
            </el-row>
          </el-card>
        </el-col>
     </el-row>
    </el-card>
          <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改【请填写功能名称】对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
<!--        <el-form-item label="设备ID" prop="deviceId">-->
<!--          <el-input v-model="form.deviceId" placeholder="请输入设备ID"/>-->
<!--        </el-form-item>-->
        <el-form-item label="图片名称" prop="imgName">
          <el-input v-model="form.imgName" placeholder="请输入图片名称"/>
        </el-form-item>
<!--        <el-form-item label="疾病">-->
<!--          <el-radio-group v-model="form.diseaseStatus">-->
<!--            <el-radio label="1">请选择字典生成</el-radio>-->
<!--          </el-radio-group>-->
<!--        </el-form-item>-->
        <el-form-item label="原始图片路径" prop="imgOrginPath">
          <el-input v-model="form.imgOrginPath" placeholder="请输入原始图片路径"/>
        </el-form-item>
        <el-form-item label="识别结果路径" prop="imgResultPath">
          <el-input v-model="form.imgResultPath" placeholder="请输入识别结果路径"/>
        </el-form-item>
<!--        <el-form-item label="状态">-->
<!--          <el-radio-group v-model="form.status">-->
<!--            <el-radio label="1">请选择字典生成</el-radio>-->
<!--          </el-radio-group>-->
<!--        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listRecog,
  getRecog,
  delRecog,
  addRecog,
  updateRecog,
  exportRecog
} from "@/api/system/recog";

export default {
  name: "Recog",
  components: {},
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
      baseUrl: 'http://124.221.206.60/recog/',
      // 【请填写功能名称】表格数据
      recogList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deviceId: null,
        imgName: null,
        diseaseStatus: null,
        imgOrginPath: null,
        imgResultPath: null,
        status: null,
      },
      // 表单参数
      form: {}
      ,
      // 表单校验
      rules: {
        imgName: [
          {
            required: true,
            message: "图片名称不能为空", trigger: "blur"
          }
        ],
      }
    }
      ;
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询【请填写功能名称】列表 */
    getList() {
      this.loading = true;
      listRecog(this.queryParams).then(response => {
        this.recogList = response.rows;
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
        imgId: null,
        deviceId: null,
        imgName: null,
        diseaseStatus: "0",
        imgOrginPath: null,
        imgResultPath: null,
        status: "0",
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      }
      ;
      this.resetForm("form");
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
      this.ids = selection.map(item => item.imgId
      )
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加【请填写功能名称】";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const imgId =
        row.imgId || this.ids
      getRecog(imgId).then(response => {
          this
            .form = response.data;
          this
            .open = true;
          this
            .title = "修改【请填写功能名称】";
        }
      )
      ;
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.imgId != null) {
              updateRecog(this.form).then(response => {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                }
              )
              ;
            } else {
              addRecog(this.form).then(response => {
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
      const imgIds = row.imgId || this.ids;
      this.$confirm('是否确认删除【请填写功能名称】编号为"' + imgIds + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delRecog(imgIds);
      }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }
      )
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有【请填写功能名称】数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportRecog(queryParams);
      }).then(response => {
          this.download(response.msg);
        }
      )
    }
  }
};
</script>
