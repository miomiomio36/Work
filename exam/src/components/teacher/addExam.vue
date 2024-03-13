<!-- 添加考试 -->
<template>
	<section class="add">
		<h3>添加考试</h3>
		<!-- courseName,exaTime,classRoomNumber,teacherId,exaState -->
		<el-form ref="form" :model="form" label-width="80px">
			<el-form-item label="考试名称">
				<el-input v-model="form.courseName"></el-input>
			</el-form-item>
			<el-form-item label="考场">
				<el-input v-model="form.classRoomNumber"></el-input>
			</el-form-item>
			<el-form-item label="考试时间">
				<el-col :span="20">
					<el-date-picker placeholder="选择日期和时间" v-model="form.exaTime" type="datetime" format="MM/DD HH:mm"
						value-format="MM/DD HH:mm" style="width: 100%;"></el-date-picker>
				</el-col>
			</el-form-item>

			<el-form-item label="监考老师">
				<el-input v-model="form.teacherId"></el-input>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="onSubmit()">立即创建</el-button>
				<el-button @click="cancel()">重置表单</el-button>
			</el-form-item>
		</el-form>
	</section>
</template>

<script>
export default {
	name: "addExam",
	data() {
		return {
			form: {
				courseName: null,
				classRoomNumber: null,
				exaTime: null,
				teacherId: null,
			}
		}
	},
	methods: {
		onSubmit() {
			// this.$axios.get('api/paperId').then( res => {
			//   // 获取后端发送来的paperId，使paperId自增
			//   this.form.paperId = res.data.data.paperId + 1
			//   this.$axios.post('api/exam', {...this.form}).then( res => {
			//     if (res.data.code == 200) {
			//       this.$message.success("添加成功！")
			//     }
			//   }).catch( () => {
			//     this.$message.error()
			//   })
			// })
		},
		// 重置表单
	},
}
</script>

<style scoped>
.add {
	padding: 0px 40px;
	width: 400px;
}
</style>