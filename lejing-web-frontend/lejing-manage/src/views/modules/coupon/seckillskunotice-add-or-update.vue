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
      <el-form-item label="member_id" prop="memberId">
        <el-input v-model="dataForm.memberId" placeholder="member_id"></el-input>
      </el-form-item>
      <el-form-item label="sku_id" prop="skuId">
        <el-input v-model="dataForm.skuId" placeholder="sku_id"></el-input>
      </el-form-item>
      <el-form-item label="活动场次id" prop="sessionId">
        <el-input v-model="dataForm.sessionId" placeholder="活动场次id"></el-input>
      </el-form-item>
      <el-form-item label="订阅时间" prop="subcribeTime">
        <el-input v-model="dataForm.subcribeTime" placeholder="订阅时间"></el-input>
      </el-form-item>
      <el-form-item label="发送时间" prop="sendTime">
        <el-input v-model="dataForm.sendTime" placeholder="发送时间"></el-input>
      </el-form-item>
      <el-form-item label="通知方式" prop="noticeType">
        <el-select v-model="dataForm.noticeType" placeholder="请选择">
          <el-option :value="0" label="短信"></el-option>
          <el-option :value="1" label="邮件"></el-option>
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
        memberId: '',
        skuId: '',
        sessionId: '',
        subcribeTime: '',
        sendTime: '',
        noticeType: ''
      },
      dataRule: {
        memberId: [
          {required: true, message: 'member_id不能为空', trigger: 'blur'}
        ],
        skuId: [
          {required: true, message: 'sku_id不能为空', trigger: 'blur'}
        ],
        sessionId: [
          {required: true, message: '活动场次id不能为空', trigger: 'blur'}
        ],
        subcribeTime: [
          {required: true, message: '订阅时间不能为空', trigger: 'blur'}
        ],
        sendTime: [
          {required: true, message: '发送时间不能为空', trigger: 'blur'}
        ],
        noticeType: [
          {
            required: true,
            message: "通知方式不能为空",
            trigger: "blur"
          }
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
            url: this.$http.adornUrl(`/coupon/seckillskunotice/info/${this.dataForm.id}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 200) {
              data.seckillSkuNotice = data.data;
              this.dataForm.memberId = data.seckillSkuNotice.memberId;
              this.dataForm.skuId = data.seckillSkuNotice.skuId;
              this.dataForm.sessionId = data.seckillSkuNotice.sessionId;
              this.dataForm.subcribeTime = data.seckillSkuNotice.subcribeTime;
              this.dataForm.sendTime = data.seckillSkuNotice.sendTime;
              this.dataForm.noticeType = data.seckillSkuNotice.noticeType;
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
            url: this.$http.adornUrl(`/coupon/seckillskunotice/${!this.dataForm.id ? 'save' : 'update'}`),
            method: !this.dataForm.id ? 'post' : 'put',
            data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
              'memberId': this.dataForm.memberId,
              'skuId': this.dataForm.skuId,
              'sessionId': this.dataForm.sessionId,
              'subcribeTime': this.dataForm.subcribeTime,
              'sendTime': this.dataForm.sendTime,
              'noticeType': this.dataForm.noticeType
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
