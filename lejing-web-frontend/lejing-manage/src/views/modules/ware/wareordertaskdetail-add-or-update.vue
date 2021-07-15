<template>
  <el-dialog
    :close-on-click-modal="false"
    :title="!dataForm.id ? '新增' : '修改'"
    :visible.sync="visible">
    <el-form ref="dataForm" :model="dataForm" :rules="dataRule" label-width="120px"
             @keyup.enter.native="dataFormSubmit()">
      <el-form-item label="sku_id" prop="skuId">
        <el-input v-model="dataForm.skuId" placeholder="sku_id"></el-input>
      </el-form-item>
      <el-form-item label="sku_name" prop="skuName">
        <el-input v-model="dataForm.skuName" placeholder="sku_name"></el-input>
      </el-form-item>
      <el-form-item label="购买个数" prop="skuNum">
        <el-input v-model="dataForm.skuNum" placeholder="购买个数"></el-input>
      </el-form-item>
      <el-form-item label="工作单id" prop="taskId">
        <el-input v-model="dataForm.taskId" placeholder="工作单id"></el-input>
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
        skuName: '',
        skuNum: '',
        taskId: ''
      },
      dataRule: {
        skuId: [
          {required: true, message: 'sku_id不能为空', trigger: 'blur'}
        ],
        skuName: [
          {required: true, message: 'sku_name不能为空', trigger: 'blur'}
        ],
        skuNum: [
          {required: true, message: '购买个数不能为空', trigger: 'blur'}
        ],
        taskId: [
          {required: true, message: '工作单id不能为空', trigger: 'blur'}
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
            url: this.$http.adornUrl(`/ware/wareordertaskdetail/info/${this.dataForm.id}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 200) {
              data.wareOrderTaskDetail = data.data;
              this.dataForm.skuId = data.wareOrderTaskDetail.skuId;
              this.dataForm.skuName = data.wareOrderTaskDetail.skuName;
              this.dataForm.skuNum = data.wareOrderTaskDetail.skuNum;
              this.dataForm.taskId = data.wareOrderTaskDetail.taskId;
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
            url: this.$http.adornUrl(`/ware/wareordertaskdetail/${!this.dataForm.id ? 'save' : 'update'}`),
            method: !this.dataForm.id ? 'post' : 'put',
            data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
              'skuId': this.dataForm.skuId,
              'skuName': this.dataForm.skuName,
              'skuNum': this.dataForm.skuNum,
              'taskId': this.dataForm.taskId
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
