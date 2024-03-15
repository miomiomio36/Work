<template>
	<div class="exam">
		<el-table :data="pagination.records" border>
			<el-table-column fixed="left" prop="examId" label="考试编号" width="180"></el-table-column>
			<el-table-column prop="courseName" label="考试名称" width="200"></el-table-column>
			<el-table-column prop="examTim" label="考试时间" width="120"></el-table-column>
			<el-table-column prop="classroomNumber" label="考场" width="120"></el-table-column>
			<el-table-column prop="teacherId" label="监考老师" width="120"></el-table-column>
			<el-table-column prop="exaState" label="考试状态" width="120"></el-table-column>
			<el-table-column label="操作" width="150">
				<template v-slot="scope">
					<el-button @click="add(scope.row.paperId,scope.row.source)" type="primary"
						size="small">增加题库</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
			v-model:currentPage="pagination.current" :page-sizes="[4, 8, 10, 20]" v-model:page-size="pagination.size"
			layout="total, sizes, prev, pager, next, jumper" :total="pagination.total" class="page">
		</el-pagination>
	</div>
</template>

<script>
	import router from "@/router";

	export default {
		name: "addAnswer",
		data() {
			return {
				form: {}, //保存点击以后当前试卷的信息
				pagination: { //分页后的考试信息
					current: 1, //当前页
					total: 0, //记录条数
					size: 4, //每页条数
					records: [ //测试数据
						{
							examId: '1',
							courseName: '数据库',
							examTim: '03-20 14:00',
							classroomNumber: 'A101',
							teacherId: '001',
							exaState: '未开始'
						},
						{
							examId: '2',
							courseName: '英语',
							examTim: '2024-04-15',
							classroomNumber: 'B203',
							teacherId: '002',
							exaState: '已结束'
						},
						{
							examId: '3',
							courseName: 'Java',
							examTim: '2024-05-02',
							classroomNumber: 'C305',
							teacherId: '003',
							exaState: '进行中'
						}
					]
				},

			}
		},
		methods: {
			getExamInfo() {
				this.$axios.get('api/exams/' + this.pagination.current + '/' + this.pagination.size)
					.then(res => {
						this.pagination = res.data.data
					})
			},
			//改变当前记录条数
			handleSizeChange(val) {
				this.pagination.size = val
				this.getExamInfo()
			},
			//改变当前页码，重新发送请求
			handleCurrentChange(val) {
				this.pagination.current = val
				this.getExamInfo()
			},
			add(paperId, source) { //增加题库
				router.push({
					path: '/addAnswerChildren',
					query: {
						paperId: paperId,
						subject: source
					}
				})
			}
		},
		created() {
			// this.getExamInfo()
		}
	}
</script>

<style scoped>
	.exam {
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
	}
</style>