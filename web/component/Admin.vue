<template>
  <div id="app">
    <br>
    <!--搜索框-->
    <el-row>
        <el-col :span="1" class="grid">
            <el-button type="success" icon="el-icon-search" size="mini">查询</el-button>
        </el-col>
    </el-row>
    <br>
    <!--表格数据及操作-->
    <el-table :data="tableData" border style="width: 100%" stripe ref="multipleTable" tooltip-effect="dark">
        <!--勾选框-->
        <el-table-column type="selection" width="55">
        </el-table-column>
        <!--索引-->
        <el-table-column type="index" :index="indexMethod">
        </el-table-column>
        <el-table-column prop="id" label="账号" width="250" sortable>
        </el-table-column>
        <el-table-column prop="name" label="姓名" width="250" sortable>
        </el-table-column>
        <el-table-column label="编辑" width="150">
            <template slot-scope="scope">
                <el-button type="primary" icon="el-icon-edit" size="mini">编辑</el-button>
            </template>
        </el-table-column>
        <el-table-column label="删除" width="150">
            <template slot-scope="scope">
                <el-button type="danger" icon="el-icon-delete" size="mini">删除</el-button>
            </template>
        </el-table-column>
    </el-table>
    <br>
    <!--新增按钮-->
    <el-col :span="1" class="grid">
        <el-button type="success" icon="el-icon-circle-plus-outline" size="mini" round>新增</el-button>
    </el-col>
    <br>
  </div>
</template>
<script>
export default {
  data () {
    return {
      tableData: [{
        id: '2016',
        name: 'a小虎'
      }, {
        id: '1111',
        name: 'b小虎'
      }, {
        id: '1111',
        name: 'c小虎'
      }, {
        id: '1111',
        name: '王小虎'
      }, {
        id: '1111',
        name: '王小虎'
      }],
      input: '',
      activeIndex: '1',
      activeIndex2: '1'
    }
  },
  methods: {
    handleSelect (key, keyPath) {
      console.log(key, keyPath)
    },
    indexMethod (index) {
      return index
    }
  }
}
</script>
<style>
  #app {
    font-family: Helvetica, sans-serif;
    text-align: center;
  }
</style>
