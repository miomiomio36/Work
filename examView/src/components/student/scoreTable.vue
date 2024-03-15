<template>
	<div class="table">
		<p class="title">我的分数</p>
		<section class="content-el">
			<el-table ref="filterTable" :data="score" v-loading="loading">
				<el-table-column prop="examId" label="考试编号" width="150" filter-placement="bottom-end">
					<template v-slot="scope">
						<el-tag>{{ scope.row.examId }}</el-tag>
					</template>
				</el-table-column>
				<el-table-column prop="courseName" label="考试名称" width="200" filter-placement="bottom-end">
					<template v-slot="scope">
						<el-tag>{{ scope.row.courseName }}</el-tag>
					</template>
				</el-table-column>
				<el-table-column prop="grade" label="考试分数" width="150"></el-table-column>
				<el-table-column label="是否及格" width="150">
					<template v-slot="scope">
						<el-tag :type="scope.row.grade >= 60 ? 'success' : 'danger'">{{ scope.row.grade >= 60 ? "及格" :
							"不及格" }}</el-tag>
					</template>
				</el-table-column>
				<el-table-column label="操作" width="150">
					<template v-slot="scope">
						<el-button v-if="scope.row.grade < 60" @click="showConfirmDialog = true" type="primary"
							size="small">
							{{ apply }}
						</el-button>
					</template>
				</el-table-column>
			</el-table>
			<el-row type="flex" justify="center" align="middle" class="pagination">
				<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
					:current-page="pagination.current" :page-sizes="[4, 6, 8, 10]" :page-size="pagination.size"
					layout="total, sizes, prev, pager, next, jumper" :total="pagination.total">
				</el-pagination>
			</el-row>
			<!-- 确认补考信息弹窗 -->
			<el-dialog title="确认补考信息" :visible.sync="showConfirmDialog" width="30%">
				<span>确认补考信息</span>
				<span slot="footer" class="dialog-footer">
					<el-button @click="cancelReexamination">取消</el-button>
					<el-button type="primary" @click="confirmReexamination">确认</el-button>
				</span>
			</el-dialog>
		</section>
	</div>
</template>

<script>
// import axios from '../../api/api.js'
import { GetUserData } from '@/auth/auth.js';
const apiUrl = 'http://127.0.0.1:8888';
export default {
	name: "scoreTable",
	data() {
		return {
			pagination: { //分页后的留言列表
				current: 1, //当前页
				total: null, //记录条数
				size: 10 //每页条数
			},
			loading: false, //加载标识符
			score: [], //学生成绩
			filter: null, //过滤参数
			scoreData: [{
				examId: 11,
				courseName: '数据库',
				etScore: 80

			},
			{
				examId: 12,
				courseName: '操作系统',
				etScore: 55

			},
			{
				examId: 13,
				courseName: 'Web',
				etScore: 100
			},
			{
				examId: 13,
				courseName: 'Web',
				etScore: 20
			},
			],
			showConfirmDialog: false, // 控制确认补考信息弹窗的显示与隐藏
			apply: "申请补考",
		}
	},
	created() {
		
		this.loading = true //数据加载则遮罩表格
		// 在组件创建时加载测试数据
		this.score = this.scoreData;
		this.pagination.total = this.scoreData.length;
		this.getScore()
	},
	methods: {
		getScore() {
			// 假设你已经从某处获取了 token，例如从 localStorage 中
			const token = localStorage.getItem('token');

			// const token = JSON.parse(token1);
			console.log("token:");
			console.log(token);
			// 设置默认的请求头，其中包含 Authorization 字段
			// axios.defaults.headers['token'] = `${token}`;
			// axios.defaults.header.commons['token'] = `${token}`;
			const user = GetUserData();
			const Id = user.sid;
			try {
				this.$http.ajaxGet(`http://localhost:8888/student/grade?sid=${Id}`,token).then(res => { 
					let response = JSON.parse(res);
					console.log(response);
					console.log("res:")
					console.log(res)
					if (response.code == 200) {
						console.log("有成绩了");
						this.loading = false //数据加载完成去掉遮罩
						this.scoreData = response.data
						this.score = response.data
						console.log("成绩score:", this.score);
						console.log("数据scoreData",this.scoreData);
					}
				});
				// this.$axios.get(`student/grade/${Id}`,{
				// 	headers: {
						
				// 		'Token': `${token}`,
				// 	},
				// }
				// ).then(res => { 
				// 	console.log(res.data);
				// });
				
			} catch (error) {
				console.error('Login failed:', error);
			}
			// this.$axios('api/score/' + this.pagination.current + '/' + this.pagination.size + '/' + studentId).then(
			// 	res => {
			// 		if (res.data.code == 200) {
			// 			this.loading = false //数据加载完成去掉遮罩
			// 			this.score = res.data.data.records
			// 			this.pagination = {
			// 				...res.data.data
			// 			}
			// 			// eslint-disable-next-line no-unused-vars
			// 			let mapVal = this.score.map((element, index) => { //通过map得到 filter:[{text,value}]形式的数组对象
			// 				let newVal = {}
			// 				newVal.text = element.answerDate
			// 				newVal.value = element.answerDate
			// 				return newVal
			// 			})
			// 			let hash = []
			// 			const newArr = mapVal.reduce((item, next) => { //对新对象进行去重操作
			// 				hash[next.text] ? '' : hash[next.text] = true && item.push(next);
			// 				return item
			// 			}, []);
			// 			this.filter = newArr
			// 		}
			// 	})
		},
		applyForReexamination(row) {
			// 点击申请补考按钮时弹出确认补考信息弹窗
			this.showConfirmDialog = true;
		},
		confirmReexamination() {
			// 处理确认补考的逻辑，这里可以将申请补考按钮变为已申请状态
			console.log("确认补考");
			apply = "已申请";
			this.showConfirmDialog = false;
		},
		cancelReexamination() {
			// 处理取消补考的逻辑，这里可以隐藏弹窗
			console.log("取消补考");
			this.showConfirmDialog = false;
		},
		//改变当前记录条数
		handleSizeChange(val) {
			this.pagination.size = val
			this.getScore()
		},
		//改变当前页码，重新发送请求
		handleCurrentChange(val) {
			this.pagination.current = val
			this.getScore()
		},
		// eslint-disable-next-line no-unused-vars
		formatter(row, column) {
			return row.address;
		},
		filterTag(value, row) {
			return row.tag === value;
		},
		filterHandler(value, row, column) {
			const property = column["property"];
			return row[property] === value;
		}
	}
}
</script>

<style scoped>
.pagination {
	padding-top: 20px;
}

.table {
	width: 980px;
	margin: 0 auto;

	.title {
		margin: 20px;
	}

	.content-el {
		background-color: #fff;
		padding: 20px;
	}
}
</style>