<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" clearable placeholder="参数名"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table
      v-loading="dataListLoading"
      :data="dataList"
      border
      style="width: 100%;"
      @selection-change="selectionChangeHandle"
    >
      <el-table-column align="center" header-align="center" type="selection" width="50"></el-table-column>
      <el-table-column align="center" header-align="center" label="id" prop="id"></el-table-column>
      <el-table-column align="center" header-align="center" label="优惠券id" prop="couponId"></el-table-column>
      <el-table-column align="center" header-align="center" label="会员id" prop="memberId"></el-table-column>
      <el-table-column align="center" header-align="center" label="会员名字" prop="memberNickName"></el-table-column>
      <el-table-column align="center" header-align="center" label="获取方式" prop="getType">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.getType==0" type="primary">后台赠送</el-tag>
          <el-tag v-else type="success">主动领取</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" header-align="center" label="创建时间" prop="createTime"></el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="使用状态"
        prop="useType"
      >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.useType==0" type="primary">未使用</el-tag>
          <el-tag v-if="scope.row.useType==1" type="success">已使用</el-tag>
          <el-tag v-if="scope.row.useType==2" type="warning">已过期</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" header-align="center" label="使用时间" prop="useTime"></el-table-column>
      <el-table-column align="center" header-align="center" label="订单id" prop="orderId"></el-table-column>
      <el-table-column align="center" header-align="center" label="订单号" prop="orderSn"></el-table-column>
    </el-table>
    <el-pagination
      :current-page="pageIndex"
      :page-size="pageSize"
      :page-sizes="[10, 20, 50, 100]"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
    ></el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
  </div>
</template>

<script>
export default {
  data() {
    return {
      dataForm: {
        key: ""
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false
    };
  },
  components: {},
  activated() {
    this.getDataList();
  },
  methods: {
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true;
      this.$http({
        url: this.$http.adornUrl("/coupon/couponhistory/list"),
        method: "get",
        params: this.$http.adornParams({
          page: this.pageIndex,
          rows: this.pageSize,
          key: this.dataForm.key
        })
      }).then(({data}) => {
        if (data && data.code === 200) {
          this.dataList = data.data.items;
          this.totalPage = data.totalCount;
        } else {
          this.dataList = [];
          this.totalPage = 0;
        }
        this.dataListLoading = false;
      });
    },
    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.pageIndex = 1;
      this.getDataList();
    },
    // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val;
      this.getDataList();
    },
    // 多选
    selectionChangeHandle(val) {
      this.dataListSelections = val;
    },
    // 新增 / 修改
    addOrUpdateHandle(id) {
      this.addOrUpdateVisible = true;
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id);
      });
    },
    // 删除
    deleteHandle(id) {
      var ids = id
        ? [id]
        : this.dataListSelections.map(item => {
          return item.id;
        });
      this.$confirm(
        `确定对[id=${ids.join(",")}]进行[${id ? "删除" : "批量删除"}]操作?`,
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      ).then(() => {
        this.$http({
          url: this.$http.adornUrl(`/coupon/couponhistory/delete/${ids.join(",")}`),
          method: "delete",
          data: this.$http.adornData(ids, false)
        }).then(({data}) => {
          if (data && data.code === 200) {
            this.$message({
              message: "操作成功",
              type: "success",
              duration: 1500,
              onClose: () => {
                this.getDataList();
              }
            });
          } else {
            this.$message.error(data.msg);
          }
        });
      });
    }
  }
};
</script>
