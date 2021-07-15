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
      <el-form-item label="活动标题" prop="title">
        <el-input v-model="dataForm.title" placeholder="活动标题"></el-input>
      </el-form-item>
      <el-form-item label="生效日期" prop="enableStartTime">
        <el-date-picker
          v-model="dataForm.timeRange"
          end-placeholder="结束日期"
          range-separator="至"
          start-placeholder="开始日期"
          type="datetimerange"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="上下线状态" prop="status">
        <el-select v-model="dataForm.status" placeholder="上下线状态">
          <el-option :value="1" label="上线"></el-option>
          <el-option :value="0" label="下线"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="创建人" prop="userId">
        <el-input v-model="dataForm.userId" placeholder="创建人"></el-input>
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
        title: "",
        startTime: "",
        endTime: "",
        status: "",
        createTime: "",
        userId: "",
        timeRange: []
      },
      dataRule: {
        title: [
          {required: true, message: "活动标题不能为空", trigger: "blur"}
        ]
      }
    };
  },
  methods: {
    init(id) {
      this.dataForm.id = id || 0
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.id) {
          this.$http({
            url: this.$http.adornUrl(`/coupon/seckillpromotion/info/${this.dataForm.id}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 200) {
              data.seckillPromotion = data.data;
              this.dataForm.title = data.seckillPromotion.title;
              this.dataForm.startTime = data.seckillPromotion.startTime;
              this.dataForm.endTime = data.seckillPromotion.endTime;
              this.dataForm.status = data.seckillPromotion.status;
              this.dataForm.createTime = data.seckillPromotion.createTime;
              this.dataForm.userId = data.seckillPromotion.userId;
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
            url: this.$http.adornUrl(`/coupon/seckillpromotion/${!this.dataForm.id ? 'save' : 'update'}`),
            method: !this.dataForm.id ? 'post' : 'put',
            data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
              'title': this.dataForm.title,
              'startTime': this.dataForm.startTime,
              'endTime': this.dataForm.endTime,
              'status': this.dataForm.status,
              'createTime': this.dataForm.createTime,
              'userId': this.dataForm.userId
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
