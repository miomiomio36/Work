<!--教师管理页面-->
<template>
	<div class="all">
		<el-table :data="records" border>
			<el-table-column fixed="left" prop="teacherName" label="姓名" width="180"></el-table-column>
			<el-table-column prop="teacherId" label="教师工号" width="200"></el-table-column>
			<el-table-column prop="classId" label="课程" width="120"></el-table-column>
			<el-table-column prop="gender" label="性别" width="120">
				<template v-slot="scope">
					{{ scope.row.gender | formatGender }}
				</template>
			</el-table-column>
			<el-table-column prop="age" label="年龄" width="120"></el-table-column>
			<el-table-column label="操作" width="150">
				<template v-slot="scope">
					<el-button @click="checkGrade(scope.row.teacherId)" type="primary" size="small">编辑</el-button>
					<el-button @click="deleteById(scope.row.teacherId)" type="danger" size="small">删除</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
			v-model:currentPage="pagination.current" :page-sizes="[10, 20, 40, 80]" v-model:page-size="pagination.size"
			layout="total, sizes, prev, pager, next, jumper" :total="pagination.total" class="page">
		</el-pagination>
		<!-- 编辑对话框-->
		<el-dialog title="编辑教师信息" v-model="dialogVisible" width="30%">
			<section class="update">
				<el-form ref="form" :model="form" label-width="80px">
					<el-form-item label="姓名">
						<el-input v-model="form.teacherName"></el-input>
					</el-form-item>
					<el-form-item label="教师工号">
						<el-input v-model="form.teacherId"></el-input>
					</el-form-item>
					<el-form-item label="性别">
						<el-input v-model="form.gender"></el-input>
					</el-form-item>
					<el-form-item label="年龄">
						<el-input v-model="form.age"></el-input>
					</el-form-item>
					<el-form-item label="课程">
						<el-input v-model="form.classId"></el-input>
					</el-form-item>
				</el-form>
			</section>
			<span class="dialog-footer">
				<el-button @click="dialogVisible = false">取 消</el-button>
				<el-button type="primary" @click="submit()">确 定</el-button>
			</span>
		</el-dialog>
	</div>
</template>

<script>
	export default {
		name: "teacherManage",
		data() {
			return {
				pagination: {
					current: 1,
					total: 0,
					size: 10
				},
				records: [{
						teacherName: '张三',
						teacherId: '1001',
						classId: '1213',
						gender: 'm',
						age: 30
					},
					{
						teacherName: '李四',
						teacherId: '1002',
						classId: '1314',
						gender: 'f',
						age: 35
					},
					{
						teacherName: '李一',
						teacherId: '1003',
						classId: '13145',
						gender: 'f',
						age: 26
					},
				],
				form: {
					teacherName: '',
					teacherId: '',
					classId: '',
					gender: '',
					age: ''
				},
				dialogVisible: false
			}
		},
		//过滤器
		filters: {
			formatGender(value) {
				return value === 'f' ? '女' : (value === 'm' ? '男' : '');
			}
		},
		methods: {
			// 获取教师信息
			getTeacherInfo() {
				// 分页查询所有教师信息
				this.$axios.get('api/teachers/' + this.pagination.current + '/' + this.pagination.size)
					.then(res => {
						this.pagination = res.data.data
					}).catch(() => {
						this.$message.error('error')
					})
			},
			//改变当前记录条数
			handleSizeChange(val) {
				this.pagination.size = val;
				this.getStudentInfo();
			},
			//改变当前页码，重新发送请求
			handleCurrentChange(val) {
				this.pagination.current = val;
				this.getStudentInfo();
			},
			// 修改教师信息
			checkGrade(teacherId) {
				this.dialogVisible = true
				this.$axios.get('api/teacher/' + teacherId).then(res => {
					this.form = res.data.data
				}).catch(() => {
					this.$message.error('error')
				})
			},
			// 确认提交信息
			submit() {
				this.dialogVisible = false
				this.$axios.put('api/teacher', {
					...this.form
				}).then(res => {
					if (res.data.code == 200) {
						this.$message.success("修改成功！")
						// 更新后重新获取学生信息
						this.getTeacherInfo()
					}
				}).catch(() => {
					this.$message.error('error')
				})
			},
			// 删除当前教师信息
			deleteById(teacherId) {
				this.$confirm('此操作将永久删除, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					// 成功删除
					this.$axios.delete('api/teacher/' + teacherId).then(res => {
						if (res.data.code == 200) {
							this.$message.success("删除成功！")
						}
						this.getTeacherInfo()
					})
				}).catch(() => {})
			}
		},
		created() {
			// this.getTeacherInfo()
		}
	}
</script>

<style scoped>
	.all {
		padding: 0px 40px;

		.page {
			margin-top: 20px;
			display: flex;
			justify-content: center;
			align-items: center;
		}

		.edit {
			margin-left: 20px;
		}

		.el-table tr {
			background-color: #dd5862 !important;
		}
	}

	.el-table .warning-row {
		background: #000 !important;
	}

	.el-table .success-row {
		background: #dd5862;
	}
</style>