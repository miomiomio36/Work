<!--添加教师-->
<template>
  <section class="add">
	  <h3>添加老师</h3>
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="姓名">
        <el-input v-model="form.teacherName"></el-input>
      </el-form-item>
      <el-form-item label="教师工号">
        <el-input v-model="form.teacherId"></el-input>
      </el-form-item>
      <el-form-item label="课程">
        <el-input v-model="form.classId"></el-input>
      </el-form-item>
	  <el-form-item label="性别">
	  	<el-select v-model="form.gender" placeholder="请选择">
	  		<el-option v-for="item in genderOptions" :key="item.value" :label="item.label"
	  			:value="item.value"></el-option>
	  	</el-select>
	  </el-form-item>
      <el-form-item label="年龄">
        <el-input v-model="form.age"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit()">立即创建</el-button>
        <el-button @click="cancel()">取消</el-button>
      </el-form-item>
    </el-form>
  </section>
</template>

<script>
export default {
  name: "addTeacher",
  data() {
    return {
      form: {
      	teacherName: '',
      	teacherId: '',
      	classId: '',
      	gender: '',
      	age: '',
        role: "role_teacher"
      },
	  genderOptions: [ // 添加一个数组来定义性别选项
	  	{
	  		value: "m",
	  		label: "男"
	  	},
	  	{
	  		value: "f",
	  		label: "女"
	  	}
	  ]
    }
  },
  methods: {
    // 提交表单
    onSubmit() {
      this.$axios.post('api/teacher', {
        ...this.form
      }).then( res => {
        if (res.data.code == 200) {
          this.$message.success('添加成功！')
        }
      }).catch( () => {
        this.$message.error()
      })
    },
    // 重置表单
    cancel() {
      this.form = {
        role: "role_teacher"
      }
    }
  }
}
</script>

<style scoped>
.add {
  padding: 0px 40px;
  width: 400px;
}
</style>
