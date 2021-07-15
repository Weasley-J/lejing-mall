<template>
  <el-dialog
    :close-on-click-modal="false"
    :visible.sync="visible"
    title="日志列表"
    width="75%">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.id" clearable placeholder="任务ID"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table
      v-loading="dataListLoading"
      :data="dataList"
      border
      height="460"
      style="width: 100%;">
      <el-table-column
        align="center"
        header-align="center"
        label="日志ID"
        prop="logId"
        width="80">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="任务ID"
        prop="jobId"
        width="80">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="bean名称"
        prop="beanName">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="参数"
        prop="params">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="状态"
        prop="status">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 0" size="small">成功</el-tag>
          <el-tag v-else size="small" style="cursor: pointer;" type="danger"
                  @click.native="showErrorInfo(scope.row.logId)">失败
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="耗时(单位: 毫秒)"
        prop="times">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="执行时间"
        prop="createTime"
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
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      visible: false,
      dataForm: {
        id: ''
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false
    }
  },
  methods: {
    init() {
      this.visible = true
      this.getDataList()
    },
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true
      this.$http({
        url: this.$http.adornUrl('/sys/scheduleLog/list'),
        method: 'get',
        params: this.$http.adornParams({
          'page': this.pageIndex,
          'limit': this.pageSize,
          'jobId': this.dataForm.id
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
    },
    // 失败信息
    showErrorInfo(id) {
      this.$http({
        url: this.$http.adornUrl(`/sys/scheduleLog/info/${id}`),
        method: 'get',
        params: this.$http.adornParams()
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.$alert(data.log.error)
        } else {
          this.$message.error(data.msg)
        }
      })
    }
  }
}
</script>
