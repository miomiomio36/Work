// 我的考试页面组件
<template>
	<div id="myExam">
		<div class="title">我的考试</div>
		<div class="wrapper">
			<ul class="top">
				<li class="order" @click="handleCurrentChange(1)">考试安排</li>
				<li class="search-li">
					<div class="icon"><input type="text" placeholder="考试名称" class="search" v-model="key">
						<i class="el-icon-search"></i>
					</div>
				</li>
				<li><el-button type="primary" @click="search()">搜索考试</el-button></li>
			</ul>
			<ul class="paper" v-loading="loading">
				<li class="item" v-for="(item,index) in examList" :key="index">
					<h4 @click="toExamMsg(item.examCode)">{{item.source}}</h4>
					<span class="name">考场：{{item.classroomNumber}}</span>
					<span class="examState">{{item.examState}}</span>
					<div class="info">
						<i class="el-icon-loading"></i><span>考试时间：{{item.examDate}}</span>
					</div>
				</li>
			</ul>
			<!-- <div class="pagination">
				<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
					:current-page="pagination.current" :page-sizes="[6, 10, 20, 40]" :page-size="pagination.size"
					layout="total, sizes, prev, pager, next, jumper" :total="pagination.total">
				</el-pagination>
			</div> -->
		</div>
	</div>
</template>

<script>
	export default {
		name: 'myExam',
		data() {
			return {
				// 搜索关键词
				key: null,
				// 是否加载中
				loading: false,
				// 分页信息
				pagination: {
					current: 1,
					total: 0,
					size: 6,
					// records: [] // 新增数组用于存储试卷数据
				},
				examList: [{
						examCode: '1',
						source: '操作系统',
						classroomNumber:'A210',
						examState: '已结课',
						examDate: '2024-03-12 15:30',


					},
					{
						examCode: '1',
						source: '操作系统',
						classroomNumber:'A210',
						examState: '已结课',
						examDate: '2024-03-12 15:30',

					},
					{
						examCode: '1',
						source: '操作系统',
						classroomNumber:'A210',
						examState: '已结课',
						examDate: '2024-03-12 15:30',

					},
					{
						examCode: '1',
						source: '操作系统',
						classroomNumber:'A210',
						examState: '已结课',
						examDate: '2024-03-12 15:30',
					
					},
					{
						examCode: '1',
						source: '操作系统',
						classroomNumber:'A210',
						examState: '已结课',
						examDate: '2024-03-12 15:30',
					
					},
				]
			}
		},
		methods: {
			// 获取当前用户的考试信息
			getExamInfo() {
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
			// 搜索试卷
			search() {
				// this.$axios('/api/exams').then(res => {
				// 	if (res.data.code == 200) {
				// 		let allExam = res.data.data
				// 		let newPage = allExam.filter(item => {
				// 			return item.source.includes(this.key)
				// 		})
				// 		this.pagination.records = newPage
				// 	}
				// })
			},
		},
		created() {
			// this.getExamInfo()
			// this.loading = true
		}
	}
</script>

<style scoped>
	.pagination {
		padding: 20px 0px 30px 0px;
		.el-pagination {
			display: flex;
			justify-content: center;
		}
	}

	.paper {
		h4 {
			cursor: pointer;
		}
	}

	.paper .item a {
		color: #000;
	}

	.wrapper .top .order {
		cursor: pointer;
	}

	.wrapper .top .order:hover {
		color: #0195ff;
		border-bottom: 2px solid #0195ff;
	}

	.wrapper .top .order:visited {
		color: #0195ff;
		border-bottom: 2px solid #0195ff;
	}
	
	
	.examState{
		width: 150px;
		height: 25px;
		padding-left: 60px; /* 或者使用 padding-left，根据需要选择 */
		color: blue;
	}

	.item .info i {
		margin-right: 5px;
		color: #0195ff;
	}

	.item .info span {
		margin-right: 14px;
	}

	.paper .item {
		width: 380px;
		border-radius: 4px;
		padding: 20px 30px;
		border: 1px solid #eee;
		box-shadow: 0 0 4px 2px rgba(217, 222, 234, 0.3);
		transition: all 0.6s ease;
	}

	.paper .item:hover {
		box-shadow: 0 0 4px 2px rgba(140, 193, 248, 0.45);
		transform: scale(1.03);
	}

	.paper .item .info {
		font-size: 14px;
		color: #88949b;
	}

	.paper .item .name {
		font-size: 14px;
		color: #88949b;
	}

	.paper * {
		margin: 20px 0;
	}

	.wrapper .paper {
		display: flex;
		justify-content: space-around;
		flex-wrap: wrap;
	}

	.top .el-icon-search {
		position: absolute;
		right: 10px;
		top: 10px;
	}

	.top .icon {
		position: relative;
	}

	.wrapper .top {
		border-bottom: 1px solid #eee;
		margin-bottom: 20px;
	}

	#myExam .search-li {
		margin-left: auto;
	}

	.top .search-li {
		margin-left: auto;
	}

	.top li {
		display: flex;
		align-items: center;
	}

	.top .search {
		margin-left: auto;
		padding: 10px;
		border-radius: 4px;
		border: 1px solid #eee;
		box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
		transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
	}

	.top .search:hover {
		color: #0195ff;
		border-color: #0195ff;
	}

	.wrapper .top {
		display: flex;
	}

	.wrapper .top li {
		margin: 20px;
	}

	#myExam {
		width: 980px;
		margin: 0 auto;
	}

	#myExam .title {
		margin: 20px;
	}

	#myExam .wrapper {
		background-color: #fff;
	}
</style>