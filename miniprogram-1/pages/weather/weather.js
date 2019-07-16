// 引入依赖脚本
import * as echarts from '../../ec-canvas/echarts';

 
// 全局变量
let chart = null;
let date = ['', '', '', '', '', '', ''];    //日期
let tmax = [0, 0, 0, 0, 0, 0, 0];           //最高温
let tmin = [0, 0, 0, 0, 0, 0, 0];         //最低温
 
function initChart(canvas, width, height) {
  chart = echarts.init(canvas, null, {
    width: width,
    height: height
  });
  canvas.setChart(chart);
  
  chart.showLoading(); // 首次显示加载动画
  
  var option = {
    title: {
      text: '北京气温'
    },
    tooltip: {
      trigger: 'axis',
      axisPointer: {            // 坐标轴指示器，坐标轴触发有效
        type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
      }
    },
    legend: {
      data: ['最高温', '最低温']
    },

    dataZoom: [{
      type: 'inside',
      start: 0,
      end: 6
  }, {
      start: 0,
      end: 6,
      handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
      handleSize: '80%',
      handleStyle: {
          color: '#fff',
          shadowBlur: 3,
          shadowColor: 'rgba(0, 0, 0, 0.6)',
          shadowOffsetX: 2,
          shadowOffsetY: 2
      }
  }],

    xAxis: {
      data: date //全局变量
    },
    yAxis: {
    },
    series: [{
      name: '最高温',
      type: 'line',
      data: tmax//全局变量
    }, {
      name: '最低温',
      type: 'line',
      data: tmin //全局变量
    }]
  };
 
  chart.setOption(option);
  chart.hideLoading(); // 隐藏加载动画
  return chart;
};
 
Page({
  data: {
    ec: {
      onInit: initChart
    },
    pickdate: '2019-07-10'
  },
 
  onReady() {
  },
  
  bindDateChange: function(e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      pickdate: e.detail.value
    })
  },
  btn1click:function(){
    console.log("点击查询按钮")
    wx.request({
      url: 'http://192.168.137.1:5000/api/weather',
      herder: {
        "content-type": "application/json"
      },
      method: "POST",
      data:{
        pick: this.data.pickdate // 传给后端选择查询的日期
      },
      success: function(res){
        var obj = res.data
        date = obj.day
        tmax = obj.max
        tmin = obj.min
        chart.setOption({
          xAxis: {
            data: date  //全局变量
          },
          series: [{
            name: '最高温',
            data: tmax, //全局变量
            markPoint: {
              data: [
                {type: 'max', name: '最大值'},
                {type: 'min', name: '最小值'}
              ]
            },
            markLine: {
              data: [
                {type: 'average', name: '平均值'}
              ]
            },
            type: this.option1
          }, {
            name: '最低温',
            data: tmin, //全局变量
            markPoint: {
              data: [
                {type: 'max', name: '最大值'},
                {type: 'min', name: '最小值'}
              ]
            },
            markLine: {
              data: [
                {type: 'average', name: '平均值'}
              ]
            },
            type: this.option1
          }]
        });
      },
      fail: function (res) {},
      complete: function (res) {},
    });
  }
});
