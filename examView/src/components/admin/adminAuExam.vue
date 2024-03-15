<!-- 安排考试 -->
<!-- 此为vue页面模板 -->

<!-- 页面模板： -->
<template>
    <section class="add">
        <h3>添加考试</h3>
        <!-- courseName,exaTime,classRoomNumber,teacherId,exaState -->
        <el-form ref="form" :model="form" label-width="80px">
            <el-form-item label="课程号">
                <el-input v-model="form.courseId"></el-input>
            </el-form-item>
            <el-form-item label="起始时间">
                <el-col :span="20">
                    <el-date-picker placeholder="选择日期和时间" v-model="form.exaTime1" type="datetime" format="YYYY年MM月DD日HH:mm"
                        value-format="YYYY年MM月DD日HH:mm" style="width: 100%;"></el-date-picker>
                </el-col>
            </el-form-item>
            <el-form-item label="结束时间">
                    <el-col :span="20">
                        <el-date-picker placeholder="选择日期和时间" v-model="form.exaTime2" type="datetime" format="YYYY年MM月DD日HH:mm"
                            value-format="YYYY年MM月DD日HH:mm" style="width: 100%;"></el-date-picker>
                    </el-col>
                </el-form-item>

            <el-form-item label="考试状态">
                <el-input v-model="form.type"></el-input>
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
            form: {
                courseId: null, //课程
                exaTime: null,  //考试时间
                type: null,     // 状态
                exaTime1: null,
                exaTime2: null,
            },
        }
    },
    //此为默认执行，在这个模板被渲染的时候加载里面的内容
    //意思就是，当你想要一开始就有某个方法执行的时候，你就得把你想的方法放在其中
    created() {
        this.loading = true //数据加载则遮罩表格
        // this.getMethods();
    },
    //此为方法，就是将所有方法（函数）,放入其中
    methods: {
        // 这是默认的get请求模板，写死了的
        getMethods() {
            const user = GetUserData();
            const token = localStorage.getItem('token');
            const Id = user.tid;
            console.log("token:");
            console.log(token);
            this.$http.ajaxGet(`http://localhost:8888/teacher/find/grade?tid=${Id}`, token).then(res => {
                let response = JSON.parse(res);
                console.log(response);
                if (response.code == 200) {
                    this.loading = false;
                }
            });
        },
        onSubmit() {
            this.form.exaTime = this.form.exaTime1 + "-" + this.form.exaTime2;
            const user = GetUserData();
            const token = localStorage.getItem('token');
            const Id = user.tid;
            console.log("token:");
            console.log(token);
            console.log("表单数据:",this.form)
            this.$http.ajaxGet(`http://localhost:8888/admin/add/exam?courseId=${this.form.courseId}&exaTime=${this.form.exaTime}&type=${this.form.type}`, token,
            ).then(res => {
                let response = JSON.parse(res);
                console.log(response);
                // this.$message.info(response.message);
                if (response.code == 200) {
                    this.$message.success(response.message);
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