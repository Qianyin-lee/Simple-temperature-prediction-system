<template>
  <div id=app>
    <router-view></router-view>
  </div>
</template>
<style>
* {
  padding: 0;
  margin: 0;
}
</style>
<style scoped lang="scss">
$header-height: 60px;
$background-color: #545c64;
$color: #fff;

.main {
  height: 100vh;
  min-width: 800px;
  min-height: 600px;
  overflow: hidden;

  aside {
    overflow: visible;
    height: 100%;
    background-color: $background-color;
    color: $color;

    .isClossTab {
      width: 100%;
      height: $header-height;
      cursor: pointer;
      font-size: 25px;
      text-align: center;
      line-height: $header-height;
      font-weight: bold;
      border-right: 1px solid #807c7c;
      box-sizing: border-box;
    }
    .menu {
      width: 100%;
      border-right: 0;
    }
  }

  .main-header {
    background-color: $background-color;
    color: $color;

    .el-dropdown {
      cursor: pointer;
      float: right;
    }
  }

  .crumbs {
    margin-bottom: 20px;
  }

  .main-footer {
    text-align: center;
    background-color: $background-color;
    color: $color;
    line-height: 50px;
  }
}
</style>

<script>
export default {
  data () {
    return {
      isCollapse: false,
      tabWidth: 200,
      test1: 1,
      intelval: '',
      id: 'user'
    }
  },
  created () {
    if ('WebSocket' in window) {
      this.localSocket()
    } else {
      alert('当前浏览器 Not support websocket')
    }
  },
  mounted () {
    this.id = this.$route.query.id
  },
  methods: {
    handleOpen (key, keyPath) {
      console.log(key, keyPath)
    },
    handleClose (key, keyPath) {
      console.log(key, keyPath)
    },
    isClossTabFun () {
      clearInterval(this.intelval)
      if (!this.isCollapse) {
        this.intelval = setInterval(() => {
          if (this.tabWidth <= 64) clearInterval(this.intelval)
          this.tabWidth -= 1
        }, 1)
      } else {
        this.tabWidth = 200
      }
      this.isCollapse = !this.isCollapse
    },
    localSocket () {
      let that = this
      if ('WebSocket' in window) {
        console.log('您的浏览器支持 WebSocket!')
        that.ws = new WebSocket('ws://192.168.137.1:5000/test')
        that.global.setWs(that.ws)
        that.ws.onopen = that.onopen()
        that.ws.onclose = function () {
          // 关闭 websocket
          console.log('连接已关闭...')
          setTimeout(() => {
            that.localSocket()
          }, 2000)
        }
      } else {
        // 浏览器不支持 WebSocket
        console.log('您的浏览器不支持 WebSocket!')
      }
    }
  }
}
</script>
