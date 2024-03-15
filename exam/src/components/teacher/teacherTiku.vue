<!-- 教师查看题库 -->

<template>
    <el-button @click="clicked()">添加题目</el-button>
    <el-table :data="tableData" style="width: 100%" v-loading="loading">
        <el-table-column prop="number" label="题号">
            <template v-slot="scope">
                <el-tag>{{ scope.$index + 1 }}</el-tag>
            </template>
        </el-table-column>
        <el-table-column prop="question" label="题目">
            <template v-slot="scope">
                <el-tag>{{ scope.row.question }}</el-tag>
            </template>
        </el-table-column>
        <el-table-column prop="answer_A" label="选项A">
            <template v-slot="scope">
                <el-tag>{{ scope.row.answer_A }}</el-tag>
            </template>
        </el-table-column>
        <el-table-column prop="answer_B" label="选项B">
            <template v-slot="scope">
                <el-tag>{{ scope.row.answer_B }}</el-tag>
            </template>
        </el-table-column>
        <el-table-column prop="answer_C" label="选项C">
            <template v-slot="scope">
                <el-tag>{{ scope.row.answer_C }}</el-tag>
            </template>
        </el-table-column>
        <el-table-column prop="answer_D" label="选项D">
            <template v-slot="scope">
                <el-tag>{{ scope.row.answer_D }}</el-tag>
            </template>
        </el-table-column>
        <el-table-column prop="trueAnswer" label="正确答案">
            <template v-slot="scope">
                <el-tag>{{ scope.row.trueAnswer }}</el-tag>
            </template>
        </el-table-column>
    </el-table>
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        v-model:currentPage="pagination.current" :page-sizes="[10, 20, 40, 80]" v-model:page-size="pagination.size"
        layout="total, sizes, prev, pager, next, jumper" :total="pagination.total" class="page">
    </el-pagination>

    <!-- 编辑对话框-->
    <el-dialog title="编辑题目信息" v-model="dialogVisible" width="30%">
        <section class="update">
            <el-form ref="form" :model="form" label-width="80px">
                <el-form-item label="题目">
                    <el-input v-model="form.question"></el-input>
                </el-form-item>
                <el-form-item label="选项A">
                    <el-input v-model="form.answer_A"></el-input>
                </el-form-item>
                <el-form-item label="选项B">
                    <el-input v-model="form.answer_B"></el-input>
                </el-form-item>
                <el-form-item label="选项C">
                    <el-input v-model="form.answer_C"></el-input>
                </el-form-item>
                <el-form-item label="选项D">
                    <el-input v-model="form.answer_D"></el-input>
                </el-form-item>
                <el-form-item label="正确答案">
                    <el-input v-model="form.trueAnswer"></el-input>
                </el-form-item>
            </el-form>
        </section>
        <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="submit()">确 定</el-button>
        </span>
    </el-dialog>
</template>



<script>
// 导入：
import { ref, onMounted } from 'vue'; // 引入Vue 3中的ref
import { ElTable, ElTableColumn } from 'element-plus'; // 引入 Element Plus 中的表格组件
import { GetUserData } from '@/auth/auth';

export default {
    //此为组件，前端模板里面要用到的组件从这里取
    components: {
        ElTable,
        ElTableColumn,
    },
    // 此为数据导出，前端模板里要用的数据就从这里面取
    data() {
        return {
            loading: false, //加载标识符
            tableData: [],   //表数据
            pagination: { //分页后的留言列表
                current: 1, //当前页
                total: 0, //记录条数
                size: 10 //每页条数
            },
            form: {},
            dialogVisible: false,
        }
    },
    //此为默认执行，在这个模板被渲染的时候加载里面的内容
    //意思就是，当你想要一开始就有某个方法执行的时候，你就得把你想的方法放在其中
    created() {
        this.loading = true //数据加载则遮罩表格
        this.getDatas();
        this.pagination.total = this.tableData.length;
    },
    //此为方法，就是将所有方法（函数）,放入其中
    methods: {
        // 这是默认的get请求模板，写死了的
        getDatas() {
            this.pagination.total = this.tableData.length;
            const user = GetUserData();
            
            const Id = user.tid;
            console.log('id:', Id);
            const token = localStorage.getItem('token');
            console.log("token:");
            console.log(token);
            this.$http.ajaxGet(`http://localhost:8888/teacher/find/question?tid=${Id}`, token).then(res => {
                let response = JSON.parse(res);
                console.log(response);
                if (response.code == 200) {
                    this.loading = false;
                    this.$message.success(response.message);
                    this.tableData = response.data;
                    try {
                        this.pagination.total = this.tableData.length;
                    } catch {

                    }
                    console.log(this.tableData)
                }
                else {
                    this.$message.error(response.message);
                }

            });
        },
        //改变当前记录条数
        handleSizeChange(val) {
            this.pagination.size = val;
            this.getDatas();
        },
        //改变当前页码，重新发送请求
        handleCurrentChange(val) {
            this.pagination.current = val;
            this.getDatas();
        },
        clicked() {
            this.dialogVisible = true;
        },
        // 提交修改后的学生信息
        submit() {
            this.dialogVisible = false;
            const user = GetUserData();
            const courseId = user.courseId;
            this.form['id'] = null;
            this.form['courseId'] = Number(courseId);
            const token = localStorage.getItem('token');
            this.$http.ajaxPost(`http://localhost:8888/teacher/import/question`, token, this.form).then(res => { 
                let response = JSON.parse(res);
                console.log(response);
                if (response.code == 200) {
                    this.$message.success(response.message);
                    this.getDatas();
                }
                else {
                    this.$message.error(response.message);
                }
            });
        },
    },

}

</script>


<style scoped></style>