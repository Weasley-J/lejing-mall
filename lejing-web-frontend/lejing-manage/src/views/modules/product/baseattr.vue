<template>
  <el-row :gutter="20">
    <el-col :span="6">
      <category @tree-node-click="treenodeclick"></category>
    </el-col>
    <el-col :span="18">
      <div class="mod-config">
        <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
          <el-form-item>
            <el-input v-model="dataForm.key" clearable placeholder="参数名"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button @click="getDataList()">查询</el-button>
            <el-button type="success" @click="getAllDataList()">查询全部</el-button>
            <el-button
              v-if="isAuth('product:attr:save')"
              type="primary"
              @click="addOrUpdateHandle()"
            >新增
            </el-button>
            <el-button
              v-if="isAuth('product:attr:delete')"
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
          <el-table-column align="center" header-align="center" label="id" prop="attrId"></el-table-column>
          <el-table-column align="center" header-align="center" label="属性名" prop="attrName"></el-table-column>
          <el-table-column
            v-if="attrtype == 1"
            align="center"
            header-align="center"
            label="可检索"
            prop="searchType"
          >
            <template slot-scope="scope">
              <i v-if="scope.row.searchType==1" class="el-icon-success"></i>
              <i v-else class="el-icon-error"></i>
            </template>
          </el-table-column>
          <el-table-column align="center" header-align="center" label="值类型" prop="valueType">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.valueType==0" type="success">单选</el-tag>
              <el-tag v-else>多选</el-tag>
            </template>
          </el-table-column>
          <el-table-column align="center" header-align="center" label="图标" prop="icon"></el-table-column>
          <el-table-column align="center" header-align="center" label="可选值" prop="valueSelect">
            <template slot-scope="scope">
              <el-tooltip placement="top">
                <div slot="content">
                  <span v-for="(i,index) in scope.row.valueSelect.split(';')" :key="index">{{ i }}<br/></span>
                </div>
                <el-tag>{{ scope.row.valueSelect.split(";")[0] + " ..." }}</el-tag>
              </el-tooltip>
            </template>
          </el-table-column>
          <el-table-column align="center" header-align="center" label="启用" prop="enable">
            <template slot-scope="scope">
              <i v-if="scope.row.enable==1" class="el-icon-success"></i>
              <i v-else class="el-icon-error"></i>
            </template>
          </el-table-column>
          <el-table-column align="center" header-align="center" label="所属分类" prop="catelogName"></el-table-column>
          <el-table-column
            v-if="attrtype == 1"
            align="center"
            header-align="center"
            label="所属分组"
            prop="groupName"
          ></el-table-column>
          <el-table-column v-if="attrtype == 1" align="center" header-align="center" label="快速展示" prop="showDesc">
            <template slot-scope="scope">
              <i v-if="scope.row.showDesc==1" class="el-icon-success"></i>
              <i v-else class="el-icon-error"></i>
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            fixed="right"
            header-align="center"
            label="操作"
            width="150"
          >
            <template slot-scope="scope">
              <el-button size="small" type="text" @click="addOrUpdateHandle(scope.row.attrId)">修改</el-button>
              <el-button size="small" type="text" @click="deleteHandle(scope.row.attrId)">删除</el-button>
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
        <add-or-update
          v-if="addOrUpdateVisible"
          ref="addOrUpdate"
          :type="attrtype"
          @refreshDataList="getDataList"
        ></add-or-update>
      </div>
    </el-col>
  </el-row>
</template>

<script>
//这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
//例如：import 《组件名称》 from '《组件路径》';
import Category from "../common/category";
import AddOrUpdate from "./attr-add-or-update";

export default {
  //import引入的组件需要注入到对象中才能使用
  components: {Category, AddOrUpdate},
  props: {
    attrtype: {
      type: Number,
      default: 1
    }
  },
  data() {
    return {
      catId: 0,
      type: 1,
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
  activated() {
    this.getDataList();
  },
  methods: {
    //感知树节点被点击
    treenodeclick(data, node, component) {
      if (node.level == 3) {
        this.catId = data.catId;
        this.getDataList(); //重新查询
      }
    },
    getAllDataList() {
      this.catId = 0;
      this.getDataList();
    },
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true;
      let type = this.attrtype == 0 ? "sale" : "base";
      this.$http({
        url: this.$http.adornUrl(`/product/attr/${type}/list/${this.catId}`),
        method: "get",
        params: this.$http.adornParams({
          page: this.pageIndex,
          rows: this.pageSize,
          key: this.dataForm.key
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
          return item.attrId;
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
          url: this.$http.adornUrl(`/product/attr/delete/${ids.join(",")}`),
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
<style scoped>
</style>
