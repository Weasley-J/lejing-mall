<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" clearable placeholder="参数名"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('order:orderreturnapply:save')" type="primary" @click="addOrUpdateHandle()">新增
        </el-button>
        <el-button v-if="isAuth('order:orderreturnapply:delete')" :disabled="dataListSelections.length <= 0"
                   type="danger"
                   @click="deleteHandle()">批量删除
        </el-button>
      </el-form-item>
    </el-form>
    <el-table
      v-loading="dataListLoading"
      :data="dataList"
      border
      style="width: 100%;"
      @selection-change="selectionChangeHandle">
      <el-table-column
        align="center"
        header-align="center"
        type="selection"
        width="50">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="id"
        prop="id">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="order_id"
        prop="orderId">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="退货商品id"
        prop="skuId">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="订单编号"
        prop="orderSn">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="申请时间"
        prop="createTime">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="会员用户名"
        prop="memberUsername">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="退款金额"
        prop="returnAmount">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="退货人姓名"
        prop="returnName">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="退货人电话"
        prop="returnPhone">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="申请状态[0->待处理；1->退货中；2->已完成；3->已拒绝]"
        prop="status">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="处理时间"
        prop="handleTime">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="商品图片"
        prop="skuImg">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="商品名称"
        prop="skuName">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="商品品牌"
        prop="skuBrand">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="商品销售属性(JSON)"
        prop="skuAttrsVals">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="退货数量"
        prop="skuCount">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="商品单价"
        prop="skuPrice">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="商品实际支付单价"
        prop="skuRealPrice">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="原因"
        prop="reason">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="描述"
        prop="description述">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="凭证图片，以逗号隔开"
        prop="descPics">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="处理备注"
        prop="handleNote">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="处理人员"
        prop="handleMan">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="收货人"
        prop="receiveMan">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="收货时间"
        prop="receiveTime">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="收货备注"
        prop="receiveNote">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="收货电话"
        prop="receivePhone">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="公司收货地址"
        prop="companyAddress">
      </el-table-column>
      <el-table-column
        align="center"
        fixed="right"
        header-align="center"
        label="操作"
        width="150">
        <template slot-scope="scope">
          <el-button size="small" type="text" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button size="small" type="text" @click="deleteHandle(scope.row.id)">删除</el-button>
        </template>
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
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
import AddOrUpdate from './orderreturnapply-add-or-update'

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
      dataListSelections: [],
      addOrUpdateVisible: false
    }
  },
  components: {
    AddOrUpdate
  },
  activated() {
    this.getDataList()
  },
  methods: {
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true
      this.$http({
        url: this.$http.adornUrl('/order/orderreturnapply/list'),
        method: 'get',
        params: this.$http.adornParams({
          'page': this.pageIndex,
          'rows': this.pageSize,
          'key': this.dataForm.key
        })
      }).then(({data}) => {
        if (data && data.code === 200) {
          this.totalPage = data.data.totalCount;
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
    // 多选
    selectionChangeHandle(val) {
      this.dataListSelections = val
    },
    // 新增 / 修改
    addOrUpdateHandle(id) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id)
      })
    },
    // 删除
    deleteHandle(id) {
      var ids = id ? [id] : this.dataListSelections.map(item => {
        return item.id
      })
      this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl(`/order/orderreturnapply/delete/${ids.join(",")}`),
          method: 'delete',
          data: this.$http.adornData(ids, false)
        }).then(({data}) => {
          if (data && data.code === 200) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
              onClose: () => {
                this.getDataList()
              }
            })
          } else {
            this.$message.error(data.msg)
          }
        })
      })
    }
  }
}
</script>
