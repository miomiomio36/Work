<template>
    <div>
        <h2>答题页面</h2>
        <!-- <div v-for="(question, index) in Data" :key="index">
            <h3>{{ question.question }}</h3>
            <div v-for="(option, key) in question.options" :key="key">
                <label>
                    <input type="radio" :value="key" v-model="answers[index]">
                    {{ key }}. {{ option }}
                </label>
            </div>
        </div> -->
        <questionItem v-for="(item, index) in Data" :key="index" :data="item" :id="index + 1"
            @answer-selected="handleAnswerSelected(index, $event)"></questionItem>
        <!-- <div v-if="showScore">
          <h2>总得分：{{ totalScore }}</h2>
        </div> -->
        <el-button @click="submit">提交答案</el-button>

    </div>
</template>

<script>
import { reactive } from 'vue';
import questionItem from '@/components/common/question.vue'
import { GetUserData } from '@/auth/auth';
export default {
    components: {
        questionItem,
    },
    // props: {
    //     Data: {
    //         type: Array,
    //         required: true
    //     }
    // },
    data() {
        return {
            answers: [],
            score: 0,
            showScore: false,
            Data: [],
            selectedAnswers: reactive([]), // 存储每道题目的选择答案
            totalScore: 0,
            showScore: false
        };
    },
    created() {
        this.Data = JSON.parse(localStorage.getItem('question'));
        this.selectedAnswers = new Array(this.Data.length).fill('')
        console.log("题:", this.Data);
    },
    methods: {
        submit() {
            // 计算得分
            this.score = this.calculateScore();
            // 显示得分
            this.showScore = true;
            console.log("点了");
            const token = localStorage.getItem('token');
            const user = GetUserData();
            const Id = user.sid;
            this.$confirm('此操作将通过, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                const user = GetUserData();
                const Id = user.sid;
                const exaId = JSON.parse(localStorage.getItem('exaId'));
                this.$http.ajaxGet(
                    `http://localhost:8888/student/finish?sid=${Id}&exaId=${exaId}&grade=${this.totalScore}`,
                    token
                ).then(res => { 
                    let response = JSON.parse(res);
                    console.log("点击成功");
                    console.log(response);
                    if (response.code == 200) {
                        this.$message.success(response.message);
                        this.$router.push('/Index');
                    } else {
                        this.$message.error(response.message);
                    }
                });
            }).catch(() => {

            });
        },

        handleAnswerSelected(index, selectedAnswer) {
            this.selectedAnswers[index] = selectedAnswer;

        },
        calculateScore() {
            console.log("答案数组", this.selectedAnswers)
            this.totalScore = this.selectedAnswers.reduce((score, selectedAnswer, index) => {
                const correctAnswer = this.Data[index].trueAnswer;
                return score + (selectedAnswer === correctAnswer ? 2 : 0);
            }, 0);
            this.showScore = true;
        },
    }
};
</script>

<style scoped>
/* 这里是你的样式 */
</style>
