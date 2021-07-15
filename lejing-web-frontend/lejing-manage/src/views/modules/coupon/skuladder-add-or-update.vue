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
      <el-form-item label="spu_id" prop="skuId">
        <el-input v-model="dataForm.skuId" placeholder="spu_id"></el-input>
      </el-form-item>
      <el-form-item label="满几件" prop="fullCount">
        <el-input v-model="dataForm.fullCount" placeholder="满几件"></el-input>
      </el-form-item>
      <el-form-item label="打几折" prop="discount">
        <el-input v-model="dataForm.discount" placeholder="打几折"></el-input>
      </el-form-item>
      <el-form-item label="折后价" prop="price">
        <el-input v-model="dataForm.price" placeholder="折后价"></el-input>
      </el-form-item>
      <el-form-item label="是否叠加其他优惠" prop="addOther">
        <el-select v-model="dataForm.addOther" placeholder="请选择">
          <el-option :value="0" label="不可叠加"></el-option>
          <el-option :value="1" label="不可叠加"></el-option>
        </el-select>
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
      dataForm: {
        id: 0,
        skuId: '',
        fullCount: '',
        discount: '',
        price: '',
        addOther: ''
      },
      dataRule: {
        skuId: [
          {required: true, message: 'spu_id不能为空', trigger: 'blur'}
        ],
        fullCount: [
          {required: true, message: '满几件不能为空', trigger: 'blur'}
        ],
        discount: [
          {required: true, message: '打几折不能为空', trigger: 'blur'}
        ],
        price: [
          {required: true, message: '折后价不能为空', trigger: 'blur'}
        ],
        addOther: [
          {required: true, message: '是否叠加其他优惠[0-不可叠加，1-可叠加]不能为空', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    init(id) {
      this.dataForm.id = id || 0
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.id) {
          this.$http({
            url: this.$http.adornUrl(`/coupon/skuladder/info/${this.dataForm.id}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 200) {
              data.skuLadder = data.data;
              this.dataForm.skuId = data.skuLadder.skuId;
              this.dataForm.fullCount = data.skuLadder.fullCount;
              this.dataForm.discount = data.skuLadder.discount;
              this.dataForm.price = data.skuLadder.price;
              this.dataForm.addOther = data.skuLadder.addOther;
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
            url: this.$http.adornUrl(`/coupon/skuladder/${!this.dataForm.id ? 'save' : 'update'}`),
            method: !this.dataForm.id ? 'post' : 'put',
            data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
              'skuId': this.dataForm.skuId,
              'fullCount': this.dataForm.fullCount,
              'discount': this.dataForm.discount,
              'price': this.dataForm.price,
              'addOther': this.dataForm.addOther
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
