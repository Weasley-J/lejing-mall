<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" clearable placeholder="参数名"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button
          v-if="isAuth('coupon:coupon:save')"
          type="primary"
          @click="addOrUpdateHandle()"
        >新增
        </el-button>
        <el-button
          v-if="isAuth('coupon:coupon:delete')"
          :disabled="dataListSelections.length <= 0"
          type="danger"
          @click="deleteHandle()"
        >批量删除
        </el-button>
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
      <el-table-column align="center" header-align="center" label="优惠卷类型" prop="couponType">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.couponType==0">全场赠券</el-tag>
          <el-tag v-if="scope.row.couponType==1" type="info">会员赠券</el-tag>
          <el-tag v-if="scope.row.couponType==2" type="success">购物赠券</el-tag>
          <el-tag v-if="scope.row.couponType==3" type="warning">注册赠券</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" header-align="center" label="优惠券图片" prop="couponImg"></el-table-column>
      <el-table-column align="center" header-align="center" label="优惠卷名字" prop="couponName"></el-table-column>
      <el-table-column align="center" header-align="center" label="数量" prop="num"></el-table-column>
      <el-table-column align="center" header-align="center" label="金额" prop="amount"></el-table-column>
      <el-table-column align="center" header-align="center" label="每人限领张数" prop="perLimit"></el-table-column>
      <el-table-column align="center" header-align="center" label="使用门槛" prop="minPoint"></el-table-column>
      <el-table-column align="center" header-align="center" label="开始时间" prop="startTime"></el-table-column>
      <el-table-column align="center" header-align="center" label="结束时间" prop="endTime"></el-table-column>
      <el-table-column align="center" header-align="center" label="使用类型" prop="useType">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.useType==0">全场通用</el-tag>
          <el-tag v-if="scope.row.useType==1" type="info">指定分类</el-tag>
          <el-tag v-if="scope.row.useType==2" type="success">指定商品</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" header-align="center" label="备注" prop="note"></el-table-column>
      <el-table-column align="center" header-align="center" label="发行数量" prop="publishCount"></el-table-column>
      <el-table-column align="center" header-align="center" label="已使用数量" prop="useCount"></el-table-column>
      <el-table-column align="center" header-align="center" label="领取数量" prop="receiveCount"></el-table-column>
      <el-table-column label="可以领取的日期">
        <el-table-column align="center" header-align="center" label="开始日期" prop="enableStartTime"></el-table-column>
        <el-table-column align="center" header-align="center" label="结束日期" prop="enableEndTime"></el-table-column>
      </el-table-column>
      <el-table-column align="center" header-align="center" label="优惠码" prop="code"></el-table-column>
      <el-table-column align="center" header-align="center" label="领取所需等级" prop="memberLevel">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.memberLevel==0">不限等级</el-tag>
          <el-tag v-else type="info">{{ getLevel(scope.row.memberLevel) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" header-align="center" label="发布状态" prop="publish">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.publish==0">未发布</el-tag>
          <el-tag v-else type="success">已发布</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" fixed="right" header-align="center" label="操作" width="150">
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
      @current-change="currentChangeHandle"
    ></el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
import AddOrUpdate from './coupon-add-or-update'

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
    this.getMemberLevels();
  },
  methods: {
    getLevel(level) {
      let name = this.memberLevels["level_" + level];
      if (name) {
        return name;
      } else {
        return "";
      }
    },
    getMemberLevels() {
      //获取所有的会员等级
      this.$http({
        url: this.$http.adornUrl("/member/memberlevel/list"),
        method: "get",
        params: this.$http.adornParams({
          page: 1,
          limit: 500
        })
      }).then(({data}) => {
        data.page.list.forEach(item => {
          this.memberLevels["level_" + item.id] = item.name;
        });
      });
    },
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true
      this.$http({
        url: this.$http.adornUrl('/coupon/coupon/list'),
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
          url: this.$http.adornUrl(`/coupon/coupon/delete/${ids.join(",")}`),
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
