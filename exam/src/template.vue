<!-- 此为vue页面模板 -->

<!-- 页面模板： -->
<template>
    <!-- 表格展示: -->
    <!-- el-table中， :data="tableName" 作用为把表里要展示的数据设置为script中return的某个数据,即把scope设置为tableName-->
    <!-- scope应该是用作展示的某个样表 -->
    <!-- el-table-column  按列展示， prop="key"是表头的键，label="名字"为展示标签 -->
    <!-- 在el-table-column模板中，加入<template></template> -->
    <!-- v-slot="scope", 插入里面的数据为scope -->
    <!-- 最后 <el-tag>{{ scope.row.key }}</el-tag>  展示值-->
    <!-- v-loading 为遮罩 -->


    <el-table :data="tableData" style="width: 100%" v-loading="loading">
        <el-table-column prop="classname" label="课程">
            <template v-slot="scope">
                <el-tag>{{ scope.row.classname }}</el-tag>
            </template>
        </el-table-column>
        <el-table-column prop="studentId" label="学号">
            <template v-slot="scope">
                <el-tag>{{ scope.row.studentId }}</el-tag>
            </template>
        </el-table-column>
        <el-table-column prop="name" label="姓名">
            <template v-slot="scope">
                <el-tag>{{ scope.row.name }}</el-tag>
            </template>
        </el-table-column>
        <el-table-column prop="grade" label="成绩">
            <template v-slot="scope">
                <el-tag>{{ scope.row.grade }}</el-tag>
            </template>
        </el-table-column>

        <!-- 操作： -->
        <el-table-column label="操作" width="150">
            <template v-slot="scope">
                <el-button v-if="scope.row.grade < 60" @click="clicked(scope.row.studentId)" type="primary" size="small">
                    {{ apply }}
                </el-button>
            </template>
        </el-table-column>

    </el-table>
    <!-- 分页： -->
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        v-model:currentPage="pagination.current" :page-sizes="[10, 20, 40, 80]" v-model:page-size="pagination.size"
        layout="total, sizes, prev, pager, next, jumper" :total="pagination.total" class="page">
    </el-pagination>

    <!-- 请求表单 -->
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
            // 表单数据
            form: {
                courseName: null,
                classRoomNumber: null,
                exaTime: null,
                teacherId: null,
            },
            apply: "申请补考",
        }
    },
    //此为默认执行，在这个模板被渲染的时候加载里面的内容
    //意思就是，当你想要一开始就有某个方法执行的时候，你就得把你想的方法放在其中
    created() {
        this.loading = true //数据加载则遮罩表格
        this.getDatas();
    },
    //此为方法，就是将所有方法（函数）,放入其中
    methods: {
        // 这是默认的get请求模板，写死了的
        getDatas() {
            const user = GetUserData();
            const token = localStorage.getItem('token');
            const Id = user.tid;
            console.log("token:");
            console.log(token);
            this.$http.ajaxGet(`http://localhost:8888/teacher/find/grade?tid=${Id}`, token).then(res => {
                let response = JSON.parse(res);
                console.log(response);

                if (response.code == 200) {
                    this.$message.success(response.message);
                    this.loading = false;
                }
                else {
                    this.$message.error(response.message);
                }
            });
        },

        // 表单提交函数
        onSubmit() {
            const user = GetUserData();
            const token = localStorage.getItem('token');
            const Id = user.tid;
            console.log("token:");
            console.log(token);
            this.$http.ajaxPost('http://localhost:8888/admin/add/exam', token, {}
            ).then(res => {
                let response = JSON.parse(res);
                console.log(response);
                this.$message.info(response.message);
            });


        },

        // 操作函数
        clicked(studentId) {
            this.$confirm('此操作将通过, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {

            }).catch(() => {

            });
        },

        // 对应的分页函数：
        //改变当前记录条数
        handleSizeChange(val) {
            this.pagination.size = val;
            this.getDatas();    //再获取一遍数据
        },
        //改变当前页码，重新发送请求
        handleCurrentChange(val) {
            this.pagination.current = val;
            this.getDatas();
        },
    },

}

</script>


<style scoped></style>