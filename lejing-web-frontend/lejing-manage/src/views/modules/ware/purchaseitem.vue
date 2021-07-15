<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item label="仓库">
        <el-select v-model="dataForm.wareId" clearable placeholder="请选择仓库" style="width:120px;">
          <el-option v-for="w in wareList" :key="w.id" :label="w.name" :value="w.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="dataForm.status" clearable placeholder="请选择状态" style="width:120px;">
          <el-option :value="0" label="新建"></el-option>
          <el-option :value="1" label="已分配"></el-option>
          <el-option :value="2" label="正在采购"></el-option>
          <el-option :value="3" label="已完成"></el-option>
          <el-option :value="4" label="采购失败"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="关键字">
        <el-input v-model="dataForm.key" clearable placeholder="参数名" style="width:120px;"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button
          v-if="isAuth('ware:purchasedetail:save')"
          type="primary"
          @click="addOrUpdateHandle()"
        >新增
        </el-button>
        <el-dropdown :disabled="dataListSelections.length <= 0" @command="handleBatchCommand">
          <el-button type="danger">
            批量操作
            <i class="el-icon-arrow-down el-icon--right"></i>
          </el-button>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="delete">批量删除</el-dropdown-item>
            <el-dropdown-item command="merge">合并整单</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
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
      <el-table-column align="center" header-align="center" label="采购单id" prop="purchaseId"></el-table-column>
      <el-table-column align="center" header-align="center" label="采购商品id" prop="skuId"></el-table-column>
      <el-table-column align="center" header-align="center" label="采购数量" prop="skuNum"></el-table-column>
      <el-table-column align="center" header-align="center" label="采购金额" prop="skuPrice"></el-table-column>
      <el-table-column align="center" header-align="center" label="仓库id" prop="wareId"></el-table-column>
      <el-table-column align="center" header-align="center" label="状态" prop="status">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status==0">新建</el-tag>
          <el-tag v-if="scope.row.status==1" type="info">已分配</el-tag>
          <el-tag v-if="scope.row.status==2" type="wanring">正在采购</el-tag>
          <el-tag v-if="scope.row.status==3" type="success">已完成</el-tag>
          <el-tag v-if="scope.row.status==4" type="danger">采购失败</el-tag>
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
    <el-dialog :visible.sync="mergedialogVisible" title="合并到整单">
      <!-- id  assignee_id  assignee_name  phone   priority status -->
      <el-select v-model="purchaseId" clearable filterable placeholder="请选择">
        <el-option
          v-for="item in purchasetableData"
          :key="item.id"
          :label="item.id"
          :value="item.id"
        >
          <span style="float: left">{{ item.id }}</span>
          <span
            style="float: right; color: #8492a6; font-size: 13px"
          >{{ item.assigneeName }}：{{ item.phone }}</span>
        </el-option>
      </el-select>
      <span slot="footer" class="dialog-footer">
        <el-button @click="mergedialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="mergeItem">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import AddOrUpdate from "./purchasedetail-add-or-update";

export default {
  data() {
    return {
      dataForm: {
        key: "",
        status: "",
        wareId: ""
      },
      wareList: [],
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false,
      mergedialogVisible: false,
      purchaseId: "",
      purchasetableData: []
    };
  },
  components: {
    AddOrUpdate
  },
  activated() {
    this.getDataList();
    this.getWares();
  },
  methods: {
    mergeItem() {
      let items = this.dataListSelections.map(item => {
        return item.id;
      });
      if (!this.purchaseId) {
        this.$confirm(
          "没有选择任何【采购单】，将自动创建新单进行合并。确认吗？",
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }
        )
          .then(() => {
            this.$http({
              url: this.$http.adornUrl("/ware/purchase/merge"),
              method: "post",
              data: this.$http.adornData({items: items}, false)
            }).then(({data}) => {
              this.getDataList();
            });
          })
          .catch(() => {
          });
      } else {
        this.$http({
          url: this.$http.adornUrl("/ware/purchase/merge"),
          method: "post",
          data: this.$http.adornData(
            {purchaseId: this.purchaseId, items: items},
            false
          )
        }).then(({data}) => {
          this.getDataList();
        });
      }
      this.mergedialogVisible = false;
    },
    getUnreceivedPurchase() {
      this.$http({
        url: this.$http.adornUrl("/ware/purchase/unreceive/list"),
        method: "get",
        params: this.$http.adornParams({})
      }).then(({data}) => {
        this.purchasetableData = data.data.items;
      });
    },
    handleBatchCommand(cmd) {
      if (cmd == "delete") {
        this.deleteHandle();
      }
      if (cmd == "merge") {
        if (this.dataListSelections.length != 0) {
          this.getUnreceivedPurchase();
          this.mergedialogVisible = true;
        } else {
          this.$alert("请先选择需要合并的需求", "提示", {
            confirmButtonText: "确定",
            callback: action => {
            }
          });
        }
      }
    },
    getWares() {
      this.$http({
        url: this.$http.adornUrl("/ware/wareinfo/list"),
        method: "get",
        params: this.$http.adornParams({
          page: 1,
          rows: 500
        })
      }).then(({data}) => {
        this.wareList = data.data.items;
      });
    },
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true;
      this.$http({
        url: this.$http.adornUrl("/ware/purchasedetail/list"),
        method: "get",
        params: this.$http.adornParams({
          page: this.pageIndex,
          rows: this.pageSize,
          key: this.dataForm.key,
          status: this.dataForm.status,
          wareId: this.dataForm.wareId
        })
      }).then(({data}) => {
        if (data && data.code === 200) {
          this.dataList = data.data.items;
          this.totalPage = data.data.totalCount;
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
          url: this.$http.adornUrl(`/ware/purchasedetail/delete/${ids.join(",")}`),
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
