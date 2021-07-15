<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" clearable placeholder="参数名"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button
          v-if="isAuth('product:brand:save')"
          type="primary"
          @click="addOrUpdateHandle()"
        >新增
        </el-button>
        <el-button
          v-if="isAuth('product:brand:delete')"
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
      <el-table-column align="center" header-align="center" label="品牌id" prop="brandId"></el-table-column>
      <el-table-column align="center" header-align="center" label="品牌名" prop="name"></el-table-column>
      <el-table-column align="center" header-align="center" label="品牌logo地址" prop="logo">
        <template slot-scope="scope">
          <!-- <el-image
              style="width: 100px; height: 80px"
              :src="scope.row.logo"
          fit="fill"></el-image>-->
          <img :src="scope.row.logo" style="width: 100px; height: 80px"/>
        </template>
      </el-table-column>
      <el-table-column align="center" header-align="center" label="介绍" prop="descript"></el-table-column>
      <el-table-column align="center" header-align="center" label="显示状态" prop="showStatus">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.showStatus"
            :active-value="1"
            :inactive-value="0"
            active-color="#13ce66"
            inactive-color="#ff4949"
            @change="updateBrandStatus(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column align="center" header-align="center" label="检索首字母" prop="firstLetter"></el-table-column>
      <el-table-column align="center" header-align="center" label="排序" prop="sort"></el-table-column>
      <el-table-column align="center" fixed="right" header-align="center" label="操作" width="250">
        <template slot-scope="scope">
          <el-button size="small" type="text" @click="updateCatelogHandle(scope.row.brandId)">关联分类</el-button>
          <el-button size="small" type="text" @click="addOrUpdateHandle(scope.row.brandId)">修改</el-button>
          <el-button size="small" type="text" @click="deleteHandle(scope.row.brandId)">删除</el-button>
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

    <el-dialog :visible.sync="cateRelationDialogVisible" title="关联分类" width="30%">
      <el-popover v-model="popCatelogSelectVisible" placement="right-end">
        <category-cascader :catelogPath.sync="catelogPath"></category-cascader>
        <div style="text-align: right; margin: 0">
          <el-button size="mini" type="text" @click="popCatelogSelectVisible = false">取消</el-button>
          <el-button size="mini" type="primary" @click="addCatelogSelect">确定</el-button>
        </div>
        <el-button slot="reference">新增关联</el-button>
      </el-popover>
      <el-table :data="cateRelationTableData" style="width: 100%">
        <el-table-column label="#" prop="id"></el-table-column>
        <el-table-column label="品牌名" prop="brandName"></el-table-column>
        <el-table-column label="分类名" prop="catelogName"></el-table-column>
        <el-table-column align="center" fixed="right" header-align="center" label="操作">
          <template slot-scope="scope">
            <el-button
              size="small"
              type="text"
              @click="deleteCateRelationHandle(scope.row.id,scope.row.brandId)"
            >移除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cateRelationDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="cateRelationDialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import AddOrUpdate from "./brand-add-or-update";
import CategoryCascader from "../common/category-cascader";

export default {
  data() {
    return {
      dataForm: {
        key: ""
      },
      brandId: 0,
      catelogPath: [],
      dataList: [],
      cateRelationTableData: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false,
      cateRelationDialogVisible: false,
      popCatelogSelectVisible: false
    };
  },
  components: {
    AddOrUpdate,
    CategoryCascader
  },
  activated() {
    this.getDataList();
  },
  methods: {
    addCatelogSelect() {
      //{"brandId":1,"catelogId":2}
      this.popCatelogSelectVisible = false;
      this.$http({
        url: this.$http.adornUrl("/product/categorybrandrelation/save"),
        method: "post",
        data: this.$http.adornData({
          brandId: this.brandId,
          catelogId: this.catelogPath[this.catelogPath.length - 1]
        }, false)
      }).then(({data}) => {
        this.getCateRelation();
      });
    },
    deleteCateRelationHandle(id, brandId) {
      this.$http({
        url: this.$http.adornUrl(`/product/categorybrandrelation/delete/${[id].join(",")}`),
        method: "delete",
        data: this.$http.adornData([id], false)
      }).then(({data}) => {
        this.getCateRelation();
      });
    },
    updateCatelogHandle(brandId) {
      this.cateRelationDialogVisible = true;
      this.brandId = brandId;
      this.getCateRelation();
    },
    getCateRelation() {
      this.$http({
        url: this.$http.adornUrl("/product/categorybrandrelation/catelog/list"),
        method: "get",
        params: this.$http.adornParams({
          brandId: this.brandId
        })
      }).then(({data}) => {
        this.cateRelationTableData = data.data;
      });
    },
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true
      this.$http({
        url: this.$http.adornUrl('/product/brand/list'),
        method: 'get',
        params: this.$http.adornParams({
          'page': this.pageIndex,
          'rows': this.pageSize,
          'key': this.dataForm.key
        })
      }).then(({data}) => {
        if (data && data.code === 200) {
          this.dataList = data.data.items
          this.totalPage = data.data.totalCount
        } else {
          this.dataList = []
          this.totalPage = 0
        }
        this.dataListLoading = false
      })
    },
    updateBrandStatus(data) {
      console.log("最新信息", data);
      let {brandId, showStatus} = data;
      //发送请求修改状态
      this.$http({
        url: this.$http.adornUrl("/product/brand/update/status"),
        method: "put",
        data: this.$http.adornData({brandId, showStatus}, false)
      }).then(({data}) => {
        this.$message({
          type: "success",
          message: "状态更新成功"
        });
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
        return item.brandId
      })
      this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl(`/product/brand/delete/${ids.join(",")}`),
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
