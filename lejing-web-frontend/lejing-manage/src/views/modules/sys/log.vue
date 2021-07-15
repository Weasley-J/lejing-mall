<template>
  <div class="mod-log">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" clearable placeholder="用户名／用户操作"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table
      v-loading="dataListLoading"
      :data="dataList"
      border
      style="width: 100%">
      <el-table-column
        align="center"
        header-align="center"
        label="ID"
        prop="id"
        width="80">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="用户名"
        prop="username">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="用户操作"
        prop="operation">
      </el-table-column>
      <el-table-column
        :show-overflow-tooltip="true"
        align="center"
        header-align="center"
        label="请求方法"
        prop="method"
        width="150">
      </el-table-column>
      <el-table-column
        :show-overflow-tooltip="true"
        align="center"
        header-align="center"
        label="请求参数"
        prop="params"
        width="150">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="执行时长(毫秒)"
        prop="time">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="IP地址"
        prop="ip"
        width="150">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="创建时间"
        prop="createDate"
        width="180">
      </el-table-column>
    </el-table>
    <el-pagination
      :current-page="pageIndex"
      :page-size="pageSize"
      :page-sizes="[10, 20, 50, 100]"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle">
    </el-pagination>
  </div>
</template>

<script>
export default {
  data() {
    return {
      dataForm: {
        key: ''
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      selectionDataList: []
    }
  },
  created() {
    this.getDataList()
  },
  methods: {
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true
      this.$http({
        url: this.$http.adornUrl('/sys/log/list'),
        method: 'get',
        params: this.$http.adornParams({
          'page': this.pageIndex,
          'limit': this.pageSize,
          'key': this.dataForm.key
        })
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.dataList = data.page.list
          this.totalPage = data.page.totalCount
        } else {
          this.dataList = []
          this.totalPage = 0
        }
        this.dataListLoading = false
      })
    },
    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getDataList()
    },
    // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val
      this.getDataList()
    }
  }
}
</script>
