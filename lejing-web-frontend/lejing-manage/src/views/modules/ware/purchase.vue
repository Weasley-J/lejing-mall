<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item label="状态">
        <el-select v-model="dataForm.status" clearable placeholder="请选择状态" style="width:120px;">
          <el-option :value="0" label="新建"></el-option>
          <el-option :value="1" label="已分配"></el-option>
          <el-option :value="2" label="已领取"></el-option>
          <el-option :value="3" label="已完成"></el-option>
          <el-option :value="4" label="有异常"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="关键字">
        <el-input v-model="dataForm.key" clearable placeholder="参数名" style="width:120px;"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button
          v-if="isAuth('ware:purchase:save')"
          type="primary"
          @click="addOrUpdateHandle()"
        >新增
        </el-button>
        <el-button
          v-if="isAuth('ware:purchase:delete')"
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
      <el-table-column align="center" header-align="center" label="采购单id" prop="id"></el-table-column>
      <el-table-column align="center" header-align="center" label="采购人id" prop="assigneeId"></el-table-column>
      <el-table-column align="center" header-align="center" label="采购人名" prop="assigneeName"></el-table-column>
      <el-table-column align="center" header-align="center" label="联系方式" prop="phone"></el-table-column>
      <el-table-column align="center" header-align="center" label="优先级" prop="priority"></el-table-column>
      <el-table-column align="center" header-align="center" label="状态" prop="status">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status == 0">新建</el-tag>
          <el-tag v-if="scope.row.status == 1" type="info">已分配</el-tag>
          <el-tag v-if="scope.row.status == 2" type="warning">已领取</el-tag>
          <el-tag v-if="scope.row.status == 3" type="success">已完成</el-tag>
          <el-tag v-if="scope.row.status == 4" type="danger">有异常</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" header-align="center" label="仓库id" prop="wareId"></el-table-column>
      <el-table-column align="center" header-align="center" label="总金额" prop="amount"></el-table-column>
      <el-table-column align="center" header-align="center" label="创建日期" prop="createTime"></el-table-column>
      <el-table-column align="center" header-align="center" label="更新日期" prop="updateTime"></el-table-column>
      <el-table-column align="center" fixed="right" header-align="center" label="操作" width="150">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.status==0||scope.row.status==1"
            size="small"
            type="text"
            @click="opendrawer(scope.row)"
          >分配
          </el-button>
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
    <el-dialog :visible.sync="caigoudialogVisible" title="分配采购人员" width="30%">
      <el-select v-model="userId" filterable placeholder="请选择">
        <el-option
          v-for="item in userList"
          :key="item.userId"
          :label="item.username"
          :value="item.userId"
        ></el-option>
      </el-select>
      <span slot="footer" class="dialog-footer">
        <el-button @click="caigoudialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="assignUser">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import AddOrUpdate from "./purchase-add-or-update";

export default {
  data() {
    return {
      currentRow: {},
      dataForm: {
        key: "",
        status: ""
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false,
      caigoudialogVisible: false,
      userId: "",
      userList: [{
        "userId": 123,
        "username": "lindanda",
      }, {
        "userId": 456,
        "username": "zhangsan",
      }, {
        "userId": 789,
        "username": "zhaoliu",
      }]
    }
      ;
  },
  components: {
    AddOrUpdate
  },
  activated() {
    this.getDataList();
  },
  created() {

  },
  methods: {
    opendrawer(row) {
      this.getUserList();
      this.currentRow = row;
      this.caigoudialogVisible = true;
    },
    assignUser() {
      let _this = this;
      let user = {};
      this.userList.forEach(item => {
        if (item.userId == _this.userId) {
          user = item;
        }
      });
      this.caigoudialogVisible = false;
      this.$http({
        url: this.$http.adornUrl(
          `/ware/purchase/update`
        ),
        method: "put",
        data: this.$http.adornData({
          id: this.currentRow.id || undefined,
          assigneeId: user.userId,
          assigneeName: user.username,
          phone: user.mobile,
          status: 1
        })
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.$message({
            message: "操作成功",
            type: "success",
            duration: 1500
          });

          this.userId = "";
          this.getDataList();
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    getUserList() {
      this.$http({
        url: this.$http.adornUrl("/sys/user/list"),
        method: "get",
        params: this.$http.adornParams({
          page: 1,
          limit: 200
        })
      }).then(({data}) => {
        this.userList = data.page.list;
      });
    },
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true
      this.$http({
        url: this.$http.adornUrl('/ware/purchase/list'),
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
          url: this.$http.adornUrl(`/ware/purchase/delete/${ids.join(",")}`),
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
