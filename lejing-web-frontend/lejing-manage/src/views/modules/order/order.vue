<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" clearable placeholder="参数名"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('order:order:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('order:order:delete')" :disabled="dataListSelections.length <= 0" type="danger"
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
        label="member_id"
        prop="memberId">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="订单号"
        prop="orderSn">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="使用的优惠券"
        prop="couponId">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="create_time"
        prop="createTime">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="用户名"
        prop="memberUsername">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="订单总额"
        prop="totalAmount">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="应付总额"
        prop="payAmount">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="运费金额"
        prop="freightAmount">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="促销优化金额（促销价、满减、阶梯价）"
        prop="promotionAmount">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="积分抵扣金额"
        prop="integrationAmount">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="优惠券抵扣金额"
        prop="couponAmount">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="后台调整订单使用的折扣金额"
        prop="discountAmount">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="支付方式【1->支付宝；2->微信；3->银联； 4->货到付款；】"
        prop="payType">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="订单来源[0->PC订单；1->app订单]"
        prop="sourceType">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】"
        prop="status">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="物流公司(配送方式)"
        prop="deliveryCompany">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="物流单号"
        prop="deliverySn">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="自动确认时间（天）"
        prop="autoConfirmDay">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="可以获得的积分"
        prop="integration">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="可以获得的成长值"
        prop="growth">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="发票类型[0->不开发票；1->电子发票；2->纸质发票]"
        prop="billType">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="发票抬头"
        prop="billHeader">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="发票内容"
        prop="billContent">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="收票人电话"
        prop="billReceiverPhone">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="收票人邮箱"
        prop="billReceiverEmail">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="收货人姓名"
        prop="receiverName">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="收货人电话"
        prop="receiverPhone">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="收货人邮编"
        prop="receiverPostCode">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="省份/直辖市"
        prop="receiverProvince">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="城市"
        prop="receiverCity">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="区"
        prop="receiverRegion">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="详细地址"
        prop="receiverDetailAddress">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="订单备注"
        prop="note">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="确认收货状态[0->未确认；1->已确认]"
        prop="confirmStatus">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="删除状态【0->未删除；1->已删除】"
        prop="deleteStatus">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="下单时使用的积分"
        prop="useIntegration">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="支付时间"
        prop="paymentTime">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="发货时间"
        prop="deliveryTime">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="确认收货时间"
        prop="receiveTime">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="评价时间"
        prop="commentTime">
      </el-table-column>
      <el-table-column
        align="center"
        header-align="center"
        label="修改时间"
        prop="modifyTime">
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
import AddOrUpdate from './order-add-or-update'

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
        url: this.$http.adornUrl('/order/order/list'),
        method: 'get',
        params: this.$http.adornParams({
          'page': this.pageIndex,
          'rows': this.pageSize,
          'key': this.dataForm.key
        })
      }).then(({data}) => {
        if (data && data.code === 200) {
          this.dataList = data.data.items;
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
          url: this.$http.adornUrl(`/order/order/delete/${ids.join(",")}`),
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
