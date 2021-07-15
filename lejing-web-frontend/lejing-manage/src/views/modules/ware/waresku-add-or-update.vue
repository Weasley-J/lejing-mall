<template>
  <el-dialog
    :close-on-click-modal="false"
    :title="!dataForm.id ? '新增' : '修改'"
    :visible.sync="visible"
  >
    <el-form
      ref="dataForm"
      :model="dataForm"
      :rules="dataRule"
      label-width="120px"
      @keyup.enter.native="dataFormSubmit()"
    >
      <el-form-item label="sku_id" prop="skuId">
        <el-input v-model="dataForm.skuId" placeholder="sku_id"></el-input>
      </el-form-item>
      <el-form-item label="仓库" prop="wareId">
        <el-select v-model="dataForm.wareId" clearable placeholder="请选择仓库">
          <el-option v-for="w in wareList" :key="w.id" :label="w.name" :value="w.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="库存数" prop="stock">
        <el-input v-model="dataForm.stock" placeholder="库存数"></el-input>
      </el-form-item>
      <el-form-item label="sku_name" prop="skuName">
        <el-input v-model="dataForm.skuName" placeholder="sku_name"></el-input>
      </el-form-item>
      <el-form-item label="锁定库存" prop="stockLocked">
        <el-input v-model="dataForm.stockLocked" placeholder="锁定库存"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      visible: false,
      wareList: [],
      dataForm: {
        id: 0,
        skuId: "",
        wareId: "",
        stock: 0,
        skuName: "",
        stockLocked: 0
      },
      dataRule: {
        skuId: [{required: true, message: "sku_id不能为空", trigger: "blur"}],
        wareId: [
          {required: true, message: "仓库id不能为空", trigger: "blur"}
        ],
        stock: [{required: true, message: "库存数不能为空", trigger: "blur"}],
        skuName: [
          {required: true, message: "sku_name不能为空", trigger: "blur"}
        ]
      }
    };
  },
  created() {
    this.getWares();
  },
  methods: {
    getWares() {
      this.$http({
        url: this.$http.adornUrl("/ware/wareinfo/list"),
        method: "get",
        params: this.$http.adornParams({
          page: 1,
          rows: 500
        })
      }).then(({data}) => {
        console.log("/ware/wareinfo/list: ", data.data)
        this.wareList = data.data.items;
      });
    },
    init(id) {
      this.dataForm.id = id || 0
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.id) {
          this.$http({
            url: this.$http.adornUrl(`/ware/waresku/info/${this.dataForm.id}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 200) {
              data.wareSku = data.data;
              this.dataForm.skuId = data.wareSku.skuId;
              this.dataForm.wareId = data.wareSku.wareId;
              this.dataForm.stock = data.wareSku.stock;
              this.dataForm.skuName = data.wareSku.skuName;
              this.dataForm.stockLocked = data.wareSku.stockLocked;
            }
          })
        }
      })
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(`/ware/waresku/${!this.dataForm.id ? 'save' : 'update'}`),
            method: !this.dataForm.id ? 'post' : 'put',
            data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
              'skuId': this.dataForm.skuId,
              'wareId': this.dataForm.wareId,
              'stock': this.dataForm.stock,
              'skuName': this.dataForm.skuName,
              'stockLocked': this.dataForm.stockLocked
            })
          }).then(({data}) => {
            if (data && data.code === 200) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.visible = false
                  this.$emit('refreshDataList')
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        }
      })
    }
  }
}
</script>
