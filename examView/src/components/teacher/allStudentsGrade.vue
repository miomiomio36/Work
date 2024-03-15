<!-- 所有学生的成绩 -->
<template>
	<div class="all">
		<el-input v-model="courseId" placeholder="请输入课程编号" style="width: 200px"></el-input>
		<el-button @click="searchGrades()" type="primary">搜索</el-button>
		<br />
		<br />
		<el-table :data="pagination.records" border>
			<el-table-column fixed="left" prop="studentId" label="学号" width="180"></el-table-column>
			<el-table-column prop="studentName" label="姓名" width="150"></el-table-column>
			<el-table-column prop="courseId" label="课程编号" width="120"></el-table-column>
			<el-table-column prop="clazz" label="班级" width="100"></el-table-column>
			<el-table-column prop="grade" label="成绩" width="100"></el-table-column>
			<el-table-column label="操作" width="150">
				<template v-slot="scope">
					<el-button @click="editGrade(scope.row)" type="primary" size="small">编辑成绩</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-dialog :visible.sync="dialogVisible" title="编辑成绩" :width="'400px'" :height="'100px'">
			<el-form :model="editForm" label-width="80px">
				<el-input v-model="editForm.grade"></el-input>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="dialogVisible = false">取消</el-button>
				<el-button type="primary" @click="saveGrade">保存</el-button>
			</div>
		</el-dialog>
		<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
			:current-page="pagination.current" :page-sizes="[6, 10]" :page-size="pagination.size"
			layout="total, sizes, prev, pager, next, jumper" :total="pagination.total" class="page"></el-pagination>
	</div>
</template>

<script>
// import router from "@/router";
import { GetUserData } from '../../auth/auth.js';

export default {
	name: "allStudentsGrade",
	data() {
		return {
			pagination: {
				//分页后的考试信息
				current: 1, //当前页
				total: 0, //记录条数
				size: 6 //每页条数
			},
			courseId: '', // 新增一个用于存储课程编号的变量
			studentGrades: [],
			dialogVisible: false,
			editForm: { grade: null }, // 在这里定义 editForm 属性并初始化
			user:{},
		};
	},
	created() {
		this.user = GetUserData()
		console.log("生成前")
		this.getAllStudentGrades()
		console.log("生成后")
	},
	methods: {
		getAllStudentGrades() {
			// 这里应该是从后端获取所有学生的成绩数据
			// 可以通过 Axios 或其他方式获取
			// 以下是示例数据
			this.studentGrades = [{
				studentId: '0001',
				studentName: '张三',
				courseId: 'C001',
				clazz: 'Class A',
				grade: 80
			},
			{
				studentId: '202111111112',
				studentName: '李四',
				courseId: 'C001',
				clazz: 'Class B',
				grade: 75
			},
			{
				studentId: '202111111112',
				studentName: '王五',
				courseId: 'C002',
				clazz: 'Class A',
				grade: 90
			},
			{
				studentId: '202111111112',
				studentName: '赵六',
				courseId: 'C002',
				clazz: 'Class B',
				grade: 85
			},
			];
			this.pagination.total = this.studentGrades.length;
			this.pagination.records = this.studentGrades.slice(0, this.pagination.size);

			const token = localStorage.getItem('token');
			const Id = this.user.tid;
			console.log("跑一跑")
			this.$http.ajaxGet(`http://localhost:8888/teacher/find/grade?tid=${Id}}`, token).then(res => {
				console.log(res.data);
			});
		},

		searchGrades() {
			if (!this.courseId) {
				this.getAllStudentGrades();
				return;
			}
			const filteredGrades = this.studentGrades.filter(grade => grade.courseId === this.courseId);
			this.pagination.total = filteredGrades.length;
			this.pagination.records = filteredGrades.slice(0, this.pagination.size);
		},

		getAnswerInfo() {
			// 分页查询所有学生信息
			this.$axios('api/students/' + this.pagination.current + '/' + this.pagination.size).then(res => {
				this.pagination = res.data.data
			})
		},
		//改变当前记录条数
		handleSizeChange(val) {
			this.pagination.size = val;
			this.getAnswerInfo();
		},
		//改变当前页码，重新发送请求
		handleCurrentChange(val) {
			this.pagination.current = val;
			this.getAnswerInfo();
		},

		editGrade(row) {
			// 弹出编辑成绩的弹窗
			this.dialogVisible = true;
			// 初始化表单数据
			this.editForm = { grade: row.grade };
		},

		saveGrade() {
			// 这里可以发送请求保存修改后的成绩
			// 示例代码，实际情况需要根据后端接口调整
			// 成功保存后关闭弹窗
			this.dialogVisible = false;
		}
	},
	
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