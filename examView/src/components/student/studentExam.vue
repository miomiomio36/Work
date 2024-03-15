<!-- 学生考试模块 -->

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
        <el-table-column prop="courseName" label="课程">
            <template v-slot="scope">
                <el-tag>{{ scope.row.courseName }}</el-tag>
            </template>
        </el-table-column>
        <el-table-column prop="classroomNumber" label="教室">
            <template v-slot="scope">
                <el-tag>{{ scope.row.classroomNumber }}</el-tag>
            </template>
        </el-table-column>
        <el-table-column prop="exaTime" label="考试时间">
            <template v-slot="scope">
                <el-tag>{{ scope.row.exaTime }}</el-tag>
            </template>
        </el-table-column>
        <el-table-column prop="teacherId" label="监考老师">
            <template v-slot="scope">
                <el-tag>{{ scope.row.teacherId }}</el-tag>
            </template>
        </el-table-column>

        <!-- 操作： -->
        <el-table-column label="操作" width="150">
            <template v-slot="scope">
                <el-button @click="clicked(scope.row.id)" type="primary" size="small">
                    {{ apply }}
                </el-button>
            </template>
        </el-table-column>

    </el-table>
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
            apply: '参加考试',
        }
    },
    //此为默认执行，在这个模板被渲染的时候加载里面的内容
    //意思就是，当你想要一开始就有某个方法执行的时候，你就得把你想的方法放在其中
    created() {
        this.loading = true //数据加载则遮罩表格
        this.getMethods();
    },
    //此为方法，就是将所有方法（函数）,放入其中
    methods: {
        // 这是默认的get请求模板，写死了的
        getMethods() {
            const user = GetUserData();
            const token = localStorage.getItem('token');
            const Id = user.sid;
            console.log("token:");
            console.log(token);
            this.$http.ajaxGet(`http://localhost:8888/student/exam/${Id}`, token).then(res => {
                let response = JSON.parse(res);
                console.log(response);

                if (response.code == 200) {
                    this.loading = false;
                    this.tableData = response.data;
                    this.$message.success(response.message);
                }
                else {
                    this.$message.error(response.message);
                }
            });
        },
        clicked(exaid) {
            console.log("点了");
            const token = localStorage.getItem('token');
            const user = GetUserData();
            const Id = user.sid;
            this.$confirm('此操作将通过, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.$http.ajaxGet(`http://localhost:8888/student/attend?sid=${Id}&exaId=${exaid}`, token
                ).then(res => {
                    let response = JSON.parse(res);
                    console.log("点击成功");
                    console.log(response);
                    if (response.code == 200) {
                        this.$message.success(response.message);
                        console.log("exaId:",exaid);
                        localStorage.setItem('exaId', JSON.stringify(exaid));
                        localStorage.setItem('question', JSON.stringify(response.data));
                        this.$router.push('/Exam');
                    }
                    else {
                        this.$message.error(response.message);
                    }
                });
            }).catch(() => {

            });
        },
    },

}

</script>


<style scoped></style>