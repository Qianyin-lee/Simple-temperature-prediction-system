<template>
  <div id = "OptionA">
    <el-container class="main">
      <el-aside :width="tabWidth+'px'">
        <div>
          <div class="isClossTab" @click="isClossTabFun">
            <el-button type="primary" :class="isCollapse?'el-icon-d-arrow-right':'el-icon-d-arrow-left'"  circle></el-button>
          </div>
          <el-menu :class="'menu'" default-active="1-4-1" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" :collapse="isCollapse" background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
            <router-link to="/OptionA"><el-menu-item index="1">
              <i class="el-icon-s-home"></i>
              <span slot="title">首页</span>
            </el-menu-item></router-link>
              <el-submenu index="2">
              <template slot="title">
                <i class="el-icon-cloudy-and-sunny"></i>
                <span slot="title">天气分析</span>
              </template>
              <el-menu-item-group>
                <router-link to="/OptionA/Forecast">
                  <el-menu-item index="1-1">天气预测</el-menu-item>
                </router-link>
                <router-link to="/OptionA/History">
                  <el-menu-item index="1-2">历史记录</el-menu-item>
                </router-link>
              </el-menu-item-group>
            </el-submenu>
            <el-menu-item index="3" @click="go()">
              <i class="el-icon-user-solid"></i>
              <span slot="title">个人信息</span>
            </el-menu-item>
            <router-link to="/OptionA/Admin">
              <el-menu-item index="4">
                <i class="el-icon-setting"></i>
                <span slot="title">管理中心</span>
              </el-menu-item>
            </router-link>
          </el-menu>
        </div>
      </el-aside>
      <el-container>
        <el-header class="main-header">
          <el-dropdown>
            <div class="block"><el-avatar :size="60" icon="el-icon-user"></el-avatar></div>
            <el-dropdown-menu slot="dropdown">
            <router-link to="/"><el-dropdown-item >退出登录</el-dropdown-item></router-link>
            </el-dropdown-menu>
          </el-dropdown>
        </el-header>
        <el-main>
          <div>
            <router-view></router-view>
          </div>
        </el-main>
        <el-footer class="main-footer" height="50px">
          <p>基于Spark技术的气温预测分析系统</p>
        </el-footer>
      </el-container>
    </el-container>
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
$background-color: #657e96;
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
      border-right: 1px solid #657e96;
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
    go () {
      this.$router.push({path: '/OptionA/InformationA', query: { id: this.id }})
    },
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
    }
  }
}
</script>
