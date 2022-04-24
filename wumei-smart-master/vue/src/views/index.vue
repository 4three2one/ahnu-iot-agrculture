<template>
  <div class="app-container home">
    <el-row :gutter="40">
      <el-col :span="15">
        <el-row >
          <el-col>
            <el-card  shadow="hover" body-style="background-color:#F8F8F8;">
              <div ref = "map" style="height:480px;"></div>
            </el-card>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-card  shadow="hover">
              <div style="height: 100px">
                <div class="card-panel-text">
                  <el-col :span="12">
                    <div style = "font-size:20px;text-align: center;font-weight:bold;margin-bottom: 5px;">大棚名:{{choosegroupName}}</div>
                    <div style = "font-size:20px;text-align: center;font-weight:bold;margin-bottom: 5px;">网关连接的传感器设备信息</div>
                    <el-col :span="12">
                      <el-card class="box-card" style="background: #36a3f7">
                        <div style = "font-size:16px;font-weight:bold;text-align: center">设备总数</div>
                      </el-card>
                      <el-card class="box-card">
                        <div style = "font-size:16px;font-weight:bold;text-align: center">{{deviceCount}}</div>
                      </el-card>
                    </el-col>
                    <el-col :span="12">
                      <el-card class="box-card" style="background: #36a3f7">
                        <div style = "font-size:16px;font-weight:bold;text-align: center">设备在线总数</div>
                      </el-card>
                      <el-card class="box-card">
                        <div style = "font-size:16px;font-weight:bold;text-align: center">{{onlinedeviceCount}}</div>
                      </el-card>
                    </el-col>
                  </el-col>
                  <el-col :span="12">
                    <div ref="deviceChart" style="height: 300px"></div>
                  </el-col>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </el-col>
      <el-col :span="9">
        <el-card style="margin:-10px;" shadow="hover">
          <h3 style="font-weight:bold;display: inline">大棚环境数据</h3>
          <el-dropdown style = "margin: 10px" @command="getdata">
            <el-button type="primary">
              {{chooseDevice}}<i class="el-icon-arrow-down el-icon--right"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item v-for="(device,index) in deviceList"
                                :command = "device"
                                v-text="device.deviceName+device.categoryName"
                                />
            </el-dropdown-menu>
          </el-dropdown>
          <el-row :gutter="40" class="panel-group">
            <el-col :span="12" class="card-panel-col" v-for="(info,index) in devicemodelinfo" v-on:click.native="drawzhexian(info.id)">
              <div class="card-panel">
                <div class="card-panel-icon-wrapper icon-message">
                  <svg-icon :icon-class="info.image" class-name="card-panel-icon" />
                </div>
                <div class="card-panel-description">
                  <div class="card-panel-text">
                    {{info.name}}
                  </div>
                  <count-to :start-val="0" :end-val="info.data" :duration="1000" class="card-panel-num" />{{info.unit}}
                </div>
              </div>
            </el-col>
          </el-row>
        </el-card>
        <el-card style="margin:-10px;margin-top:20px;" shadow="hover">
          <div ref="statsChart" style="height:296px;"></div>
        </el-card>
        <el-card style="margin:-10px;margin-top:20px;" shadow="hover">
          <el-table :data="AlarmData" class="top"  style="width: 100%;">
            <el-table-column prop="title" label="报警设备编号" width="150"></el-table-column>
            <el-table-column prop="remark" label="环境因素" width="150"></el-table-column>
            <el-table-column prop="date" label="值" width="100"></el-table-column>
            <el-table-column prop="date" label="时间" width="150"></el-table-column>
          </el-table>
          <vue-seamless-scroll :data="AlarmData" :class-option="speed" style="height: 120px; overflow: hidden;">
            <el-table
              :data="AlarmData"
              style="width: 100%"
              class="bottom"
            >
              <el-table-column prop="deviceId" label="报警设备序号" width="150"></el-table-column>
              <el-table-column prop="remark" label="环境因素" width="150"></el-table-column>
              <el-table-column prop="modelData" label="值" width="100"></el-table-column>
              <el-table-column prop="createTime" label="时间" width="150"></el-table-column>
            </el-table>
          </vue-seamless-scroll>
        </el-card>
      </el-col>
    </el-row>

  </div>
</template>
<script>
  import CountTo from 'vue-count-to'
  import * as echarts from 'echarts';
  require('echarts/theme/macarons') // echarts theme
  import {
    loadBMap
  } from './map.js'
  require('echarts/extension/bmap/bmap')
  import {getInfo} from "../api/login";
  import {listGroup, listGroupById} from "../api/system/group";
  import {listDeviceByGroupId, getDeviceData, getOneDeviceData,listAlarmData} from "../api/system/device";
  import {listCategory} from "../api/system/category";
  import {getData} from "../api/system/dict/data";
  import {listTemplate} from "../api/iot/template"
  import { myStyleJson } from "../api/custom_map_config.js"
  export default {
    name: "Index",
    components: {
      CountTo
    },
    data() {
      return {
        //默认加载大棚
        startgroupid:15,
        //默认大棚名字
        choosegroupName:"无",
        userid:0,
        // 分组列表
        groupList: [],
        // 分组总数
        groupCount:0,
        // 选择设备
        chooseDevice:"请选择设备",
        chooseOnlineDevice:"请选择在线设备",
        chooseDeviceId:-1,
        //设备种类列表
        categoryList:[],
        categoryCount:0,
        // 设备列表
        deviceList: [],
        // 设备总数
        deviceCount:0,
        // 在线设备总数
        onlinedeviceCount:0,
        // emqx状态数据
        stats: {},
        // emqx统计信息
        static: {},
        // 版本号
        version: "3.8.0",
        // 服务器信息
        model:[],
        data:0,
        devicemodelinfo:[],
        AlarmData:[],
        AlarmTableData:[],
        speed:  {
          step: 0.5
        },
      };
    },
    created() {
      //两个初始化方法
      this.getAllDevice();
      this.start();
    },
    mounted() {
      this.getinfoWebSocket();
    },
    methods: {
      start(){
        listTemplate().then(response =>{
          this.model = response.rows;
        });
        listAlarmData().then(response =>{
          this.AlarmData = response.rows;
          for(let i = 0;i < this.AlarmData.length;i++){
              for(let j = 0;j < this.model.length;j++){
                 if(this.AlarmData[i].modelId===this.model[j].templateId){
                   this.AlarmData[i].remark = this.model[j].templateName;
                 }
              }
          }
        });
        listCategory().then(response =>{
          this.categoryList = response.rows;
          this.categoryCount = response.total;
        });
        listDeviceByGroupId(this.startgroupid).then(response => {
          this.deviceList = response.rows;
          this.deviceCount=response.total;
          this.getdata(this.deviceList[0]);
          //画设备柱状图
          this.drawDeviceNum()
        });
      },
      drawDeviceNum(){
        let xdata=[];
        let yCount=[],yOnline=[];
        for(let i = 0;i < this.categoryList.length; i++){
          for(let j = 0; j < this.deviceList.length; j++){
            if(this.deviceList[j].categoryId===this.categoryList[i].categoryId){
              xdata.push(this.categoryList[i].categoryName);
              break;
            }
          }
        }
        for(let i = 0;i < xdata.length;i++){
          let all = 0,Online = 0;
          for(let j = 0; j < this.deviceList.length; j++) {
            if(xdata[i] == this.deviceList[j].categoryName){
              all = all + 1;
              if(this.deviceList[j].status == 3){
                Online = Online + 1;
              }
            }
          }
          yCount.push(all);
          yOnline.push(Online);
        }
        let sum = 0;
        for (let i = 0; i < yOnline.length; i++) {
          sum += yOnline[i];
        }
        this.onlinedeviceCount = sum;
        this.drawdevice(xdata,yCount,yOnline);
      },
      getinfoWebSocket(){
        getInfo().then(response => {
          this.userid = response.user.userId;
          if ('WebSocket' in window) {
            this.websocket = new WebSocket('ws://localhost:8080/connectWebSocket/'+this.userid)
            this.initWebSocket()
          } else {
            alert('当前浏览器 Not support websocket')
          }
        })
      },
      getAllDevice() {
        listGroupById().then(response => {
          this.groupList = response.rows;
          this.groupCount=response.total;
          for(let i = 0;i < this.groupList.length;i++){
            if(this.groupList[i].groupId===this.startgroupid){
              this.choosegroupName = this.groupList[i].groupName;
            }
          }
          this.$nextTick(() => {
            loadBMap().then(() => {
              this.getmap();
              this.drawdevice();
            });
          })
        })
      },
      getmap() {
        var myChart = echarts.init(this.$refs.map);
        var option;
        // 格式化数据
        let convertData = function (data, status) {
          var res = [];
          for (var i = 0; i < data.length; i++) {
            var geoCoord = [data[i].lon, data[i].lat];
            if (geoCoord && data[i].status == status) {
              res.push({
                id: data[i].groupId,
                name:  data[i].groupName,
                value: geoCoord,
                status: data[i].status,
                networkAddress:data[i].address,
                activeTime: data[i].createTime
              });
            }
          }
          return res;
        };
        option = {
          title: {
            text: '设备组分布和状态（数量 '+this.groupCount+'）',
            target: "_blank",
            textStyle: {
              color: '#d0d2d4',
              // textBorderColor: '#fff',
              // textBorderWidth: 10,
            },
            top: 10,
            left: 'center'
          },
          tooltip: {
            trigger: 'item',
            formatter: function (params) {
              var htmlStr = '<div style="padding:5px;line-height:28px;">';
              htmlStr += "设备编号： <span style='color:#409EFF'>" + params.data.id + "</span><br />";
              htmlStr += "设备名称： <span style='color:#409EFF'>" + params.data.name + "</span><br />";
              htmlStr += "设备状态： ";
              if (params.data.status == 1) {
                htmlStr += "<span style='color:#E6A23C'>未激活</span>" + "<br />";
              } else if (params.data.status == 2) {
                htmlStr += "<span style='color:#F56C6C'>禁用</span>" + "<br />";
              } else if (params.data.status == 3) {
                htmlStr += "<span style='color:#67C23A'>在线</span>" + "<br />";
              } else if (params.data.status == 4) {
                htmlStr += "<span style='color:#909399'>离线</span>" + "<br />";
              }
              htmlStr += "激活时间： " + params.data.activeTime + "<br />";
              htmlStr += "所在地址： " + params.data.networkAddress + "<br />";
              htmlStr += '</div>';
              return htmlStr;
            }

          },
          bmap: {
            center: [118.4,31.2],
            zoom: 11,
            roam: true,
            mapStyle: {
            }
          },
          series: [{
            type: 'scatter',
            coordinateSystem: 'bmap',
            data: convertData(this.groupList, 1),
            symbolSize: 15,
            itemStyle: {
              color: '#E6A23C'
            }
          },
            {
              type: 'scatter',
              coordinateSystem: 'bmap',
              data: convertData(this.groupList, 2),
              symbolSize: 15,
              itemStyle: {
                color: '#F56C6C'
              }
            }, {
              type: 'scatter',
              coordinateSystem: 'bmap',
              data: convertData(this.groupList, 4),
              symbolSize: 15,
              itemStyle: {
                color: '#909399'
              }
            }, {
              type: 'effectScatter',
              coordinateSystem: 'bmap',
              data: convertData(this.groupList, 3),
              symbolSize: 15,
              showEffectOn: 'render',
              rippleEffect: {
                brushType: 'stroke',
                scale: 5
              },
              label: {
                formatter: '{b}',
                position: 'right',
                show: false
              },
              itemStyle: {
                color: '#67C23A',
                shadowBlur: 100,
                shadowColor: '#333'
              },
              zlevel: 1
            }
          ]
        };

        option && myChart.setOption(option);
        var bmap = myChart.getModel().getComponent("bmap").getBMap();
        bmap.setMapStyle({ styleJson: myStyleJson });
        var _this = this;
        myChart.on('click',function (params) {
            listDeviceByGroupId(params.data.id).then(response => {
              for(let i = 0;i < _this.groupList.length;i++){
                if(_this.groupList[i].groupId===params.data.id){
                  _this.choosegroupName = _this.groupList[i].groupName;
                }
              }
              _this.deviceList = response.rows;
              _this.deviceCount=response.total;
              _this.drawDeviceNum();
            });
        })

      },
      getdata(device){
        this.chooseDeviceId = device.deviceId;
        this.chooseDevice = device.deviceName;
        this.devicemodelinfo = [];
        getDeviceData(device.deviceId).then(response => {
          let temp = response.rows;
            for(let i = 0;i < response.total;i++){
                for(let j = 0; j < this.model.length; j++){
                  if(temp[i][0].modelId == this.model[j].templateId){
                     this.devicemodelinfo.push({
                       id: temp[i][0].modelId,
                       name: this.model[j].templateName,
                       data: temp[i][0].modelData,
                       unit: JSON.parse(this.model[j].specs).unit,
                       image:this.model[j].identifier,
                     });
                  }
                }
            }
            console.log(this.model);
            console.log(this.devicemodelinfo);
        });
      },
      drawzhexian(param){
          if(this.chooseDeviceId === -1){

          }else{
              getOneDeviceData(this.chooseDeviceId,param).then(response =>{
                let temp = response.rows;
                let data = [];
                let time = [];
                let title;
                for(let i = 0;i < response.total;i++) {
                  time.push(temp[i].createTime);
                  data.push({
                    value:temp[i].modelData,
                  });
                }
                for(let i = 0;i < this.devicemodelinfo.length; i++){
                  if(this.devicemodelinfo[i].id == param){
                    title = this.devicemodelinfo[i].name;
                  }
                }
                this.drawStats(time,data,title);
              });
          }
      },
      drawStats(time,data,title) {
        // 基于准备好的dom，初始化echarts实例
        let myChart = echarts.init(this.$refs.statsChart);
        var option;
        option = {
          tooltip: {
            trigger: 'axis',
          },
          title: {
            left: 'center',
            text: title
          },
          toolbox: {
            feature: {
              dataZoom: {
                yAxisIndex: 'none'
              },
              restore: {},
              saveAsImage: {}
            }
          },
          xAxis: {
            type: 'category',
            boundaryGap: false,
            data: time
          },
          yAxis: {
            type: 'value',
            boundaryGap: [0, '100%']
          },
          dataZoom: [
            {
              type: 'inside',
              start: 80,
              end: 100
            },
            {
              start: 0,
              end: 20
            }
          ],
          series: [
            {
              type: 'line',
              smooth: true,
              symbol: 'none',
              data: data
            }
          ]
        };
        option && myChart.setOption(option);
      },
      drawdevice(xdata,yCount,yOnline){
        let myChart = echarts.init(this.$refs.deviceChart);
        var option;
        const posList = [
          'left',
          'right',
          'top',
          'bottom',
          'inside',
          'insideTop',
          'insideLeft',
          'insideRight',
          'insideBottom',
          'insideTopLeft',
          'insideTopRight',
          'insideBottomLeft',
          'insideBottomRight'
        ];
        app.configParameters = {
          rotate: {
            min: -90,
            max: 90
          },
          align: {
            options: {
              left: 'left',
              center: 'center',
              right: 'right'
            }
          },
          verticalAlign: {
            options: {
              top: 'top',
              middle: 'middle',
              bottom: 'bottom'
            }
          },
          position: {
            options: posList.reduce(function (map, pos) {
              map[pos] = pos;
              return map;
            }, {})
          },
          distance: {
            min: 0,
            max: 100
          }
        };
        app.config = {
          rotate: 90,
          align: 'left',
          verticalAlign: 'middle',
          position: 'insideBottom',
          distance: 15,
          onChange: function () {
            const labelOption = {
              rotate: app.config.rotate,
              align: app.config.align,
              verticalAlign: app.config.verticalAlign,
              position: app.config.position,
              distance: app.config.distance
            };
            myChart.setOption({
              series: [
                {
                  label: labelOption
                },
                {
                  label: labelOption
                },
                {
                  label: labelOption
                },
                {
                  label: labelOption
                }
              ]
            });
          }
        };
        const labelOption = {
          show: true,
          position: app.config.position,
          distance: app.config.distance,
          align: app.config.align,
          verticalAlign: app.config.verticalAlign,
          rotate: app.config.rotate,
          formatter: '{c}  {name|{a}}',
          fontSize: 16,
          rich: {
            name: {}
          }
        };
        option = {
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            }
          },
          legend: {
            data: ['设备总数', '在线设备']
          },
          toolbox: {
            show: true,
            orient: 'vertical',
            left: 'right',
            top: 'center',
            feature: {
              mark: { show: true },
              dataView: { show: true, readOnly: false },
              magicType: { show: true, type: ['line', 'bar', 'stack'] },
              restore: { show: true },
              saveAsImage: { show: true }
            }
          },
          xAxis: [
            {
              type: 'category',
              axisTick: { show: false },
              data: xdata
            }
          ],
          yAxis: [
            {
              type: 'value'
            }
          ],
          series: [
            {
              name: '设备总数',
              type: 'bar',
              barGap: 0,
              label: labelOption,
              emphasis: {
                focus: 'series'
              },
              data: yCount
            },
            {
              name: '在线设备',
              type: 'bar',
              label: labelOption,
              emphasis: {
                focus: 'series'
              },
              data: yOnline
            },
          ]
        };
        option && myChart.setOption(option);
      },
      initWebSocket() {
        //连接错误
        this.websocket.onerror = this.setErrorMessage

        // //连接成功
        this.websocket.onopen = this.setOnopenMessage

        //收到消息的回调
        this.websocket.onmessage = this.setOnmessageMessage

        //连接关闭的回调
        this.websocket.onclose = this.setOncloseMessage

        //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
        window.onbeforeunload = this.onbeforeunload
      },
      setErrorMessage() {
        this.data = "WebSocket连接发生错误" + '   状态码：' + this.websocket.readyState;
      },
      setOnopenMessage() {
        this.data = "WebSocket连接成功" + '   状态码：' + this.websocket.readyState;
      },
      setOnmessageMessage(event) {
        var obj = JSON.parse(event.data);
        this.data = '服务端返回：' + event.data;
        for(let i = 0; i < this.groupList.length; i++){
            console.log(this.groupList[i]);
            console.log(obj.groupId);
            if(obj.groupId === this.groupList[i].groupId){
                this.groupList[i].status = obj.status;
                this.getmap();
                break;
            }
        }
        if(this.chooseDeviceId === obj.deviceId) {
          if (obj.airtemp != null){
            for(let i = 0;i < this.devicemodelinfo;i++){
              if(this.devicemodelinfo[i].image==="airtemp"){
                this.devicemodelinfo[i].data = obj.airtemp;
                  break;
              }
            }
          }
          if (obj.airhump != null){
            for(let i = 0;i < this.devicemodelinfo;i++){
              if(this.devicemodelinfo[i].image==="airhump"){
                this.devicemodelinfo[i].data = obj.airhump;
                break;
              }
            }
          }
        }
      },
      setOncloseMessage() {
        this.data = "WebSocket连接关闭" + '   状态码：' + this.websocket.readyState;
      },
      onbeforeunload() {
        this.closeWebSocket();
      },

      //websocket发送消息
      send() {
        this.websocket.send(this.text)
        this.text = ''
      },
      closeWebSocket() {
        this.websocket.close()
      },

    },
  }
</script>

<style lang="scss" >

  .anchorBL{
    display:none;
  }
  .top{
    .el-table__body-wrapper {
      display: none;
    }
  }
  .bottom {
    .el-table__header-wrapper {
      display: none;
      width: 100%;
    }
  }

  .panel-group {
    .card-panel-col {
      margin-bottom: 10px;
    }

    .card-panel {
      height: 68px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
      color: #666;
      box-shadow: 2px 2px 10px rgba(0, 0, 0, .1);

      &:hover {
        .card-panel-icon-wrapper {
          color: #fff;
        }

        .icon-message {
          background: #36a3f7;
        }

        .icon-shopping {
          background: #34bfa3
        }
      }

      .icon-message {
        color: #36a3f7;
      }

      .icon-shopping {
        color: #34bfa3
      }

      .card-panel-icon-wrapper {
        float: left;
        margin: 10px;
        padding: 10px;
        transition: all 0.38s ease-out;
        border-radius: 6px;
      }

      .card-panel-icon {
        float: left;
        font-size: 32px;
      }
      .seamless-warp {
        height: 150px;
        overflow: hidden;
      }
      .card-panel-description {
        float: right;
        font-weight: bold;
        margin: 15px;
        margin-left: 0px;

        .card-panel-text {
          line-height: 14px;
          color: rgba(0, 0, 0, 0.45);
          font-size: 14px;
          margin-bottom: 12px;
          text-align: right;
        }

        .card-panel-num {
          font-size: 18px;
        }
      }
    }
  }
</style>
