<template>
  <div>
    <el-dialog :close-on-click-modal="false" :visible.sync="visible" @closed="dialogClose">
      <el-dialog :visible.sync="innerVisible" append-to-body title="选择属性" width="40%">
        <div>
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
            @selection-change="innerSelectionChangeHandle"
          >
            <el-table-column align="center" header-align="center" type="selection"></el-table-column>
            <el-table-column align="center" header-align="center" label="属性id" prop="attrId"></el-table-column>
            <el-table-column align="center" header-align="center" label="属性名" prop="attrName"></el-table-column>
            <el-table-column align="center" header-align="center" label="属性图标" prop="icon"></el-table-column>
            <el-table-column align="center" header-align="center" label="可选值列表" prop="valueSelect"></el-table-column>
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
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button @click="innerVisible = false">取 消</el-button>
          <el-button type="primary" @click="submitAddRealtion">确认新增</el-button>
        </div>
      </el-dialog>
      <el-row>
        <el-col :span="24">
          <el-button type="primary" @click="addRelation">新建关联</el-button>
          <el-button
            :disabled="dataListSelections.length <= 0"
            type="danger"
            @click="batchDeleteRelation"
          >批量删除
          </el-button>
          <!--  -->
          <el-table
            :data="relationAttrs"
            border
            style="width: 100%"
            @selection-change="selectionChangeHandle"
          >
            <el-table-column align="center" header-align="center" type="selection" width="50"></el-table-column>
            <el-table-column label="#" prop="attrId"></el-table-column>
            <el-table-column label="属性名" prop="attrName"></el-table-column>
            <el-table-column label="可选值" prop="valueSelect">
              <template slot-scope="scope">
                <el-tooltip placement="top">
                  <div slot="content">
                    <span v-for="(i,index) in scope.row.valueSelect.split(';')" :key="index">
                      {{ i }}
                      <br/>
                    </span>
                  </div>
                  <el-tag>{{ scope.row.valueSelect.split(";")[0] + " ..." }}</el-tag>
                </el-tooltip>
              </template>
            </el-table-column>
            <el-table-column align="center" fixed="right" header-align="center" label="操作">
              <template slot-scope="scope">
                <el-button size="small" type="text" @click="relationRemove(scope.row.attrId)">移除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
//这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
//例如：import《组件名称》from'《组件路径》';

export default {
  //import引入的组件需要注入到对象中才能使用
  components: {},
  props: {},
  data() {
    //这里存放数据
    return {
      attrGroupId: 0,
      visible: false,
      innerVisible: false,
      relationAttrs: [],
      dataListSelections: [],
      dataForm: {
        key: ""
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      innerdataListSelections: []
    };
  },
  //计算属性类似于data概念
  computed: {},
  //监控data中的数据变化
  watch: {},
  //方法集合
  methods: {
    selectionChangeHandle(val) {
      this.dataListSelections = val;
    },
    innerSelectionChangeHandle(val) {
      this.innerdataListSelections = val;
    },
    addRelation() {
      this.getDataList();
      this.innerVisible = true;
    },
    batchDeleteRelation(val) {
      let postData = [];
      this.dataListSelections.forEach(item => {
        postData.push({attrId: item.attrId, attrGroupId: this.attrGroupId});
      });
      this.$http({
        url: this.$http.adornUrl("/product/attrgroup/attr/relation/delete"),
        method: "post",
        data: this.$http.adornData(postData, false)
      }).then(({data}) => {
        if (data.code == 200) {
          this.$message({type: "success", message: "删除成功"});
          this.init(this.attrGroupId);
        } else {
          this.$message({type: "error", message: data.msg});
        }
      });
    },
    //移除关联
    relationRemove(attrId) {
      let data = [];
      data.push({attrId, attrGroupId: this.attrGroupId});
      this.$http({
        url: this.$http.adornUrl("/product/attrgroup/attr/relation/delete"),
        method: "post",
        data: this.$http.adornData(data, false)
      }).then(({data}) => {
        if (data.code == 200) {
          this.$message({type: "success", message: "删除成功"});
          this.init(this.attrGroupId);
        } else {
          this.$message({type: "error", message: data.msg});
        }
      });
    },
    submitAddRealtion() {
      this.innerVisible = false;
      //准备数据
      console.log("准备新增的数据", this.innerdataListSelections);
      if (this.innerdataListSelections.length > 0) {
        let postData = [];
        this.innerdataListSelections.forEach(item => {
          postData.push({attrId: item.attrId, attrGroupId: this.attrGroupId});
        });
        this.$http({
          url: this.$http.adornUrl("/product/attrgroup/attr/relation"),
          method: "post",
          data: this.$http.adornData(postData, false)
        }).then(({data}) => {
          if (data.code == 200) {
            this.$message({type: "success", message: "新增关联成功"});
          }
          this.$emit("refreshData");
          this.init(this.attrGroupId);
        });
      } else {
      }
    },
    init(id) {
      this.attrGroupId = id || 0;
      this.visible = true;
      this.$http({
        url: this.$http.adornUrl(
          "/product/attrgroup/" + this.attrGroupId + "/attr/relation"
        ),
        method: "get",
        params: this.$http.adornParams({})
      }).then(({data}) => {
        this.relationAttrs = data.data;
      });
    },
    dialogClose() {
    },

    //========
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true;
      this.$http({
        url: this.$http.adornUrl(
          "/product/attrgroup/" + this.attrGroupId + "/noattr/relation"
        ),
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
    }
  }
};
</script>
<style scoped>
</style>
