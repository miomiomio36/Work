<template >
    <div class="question-item">
        <h3 class="question">{{ id }}.{{ question }}</h3>
        <div class="options">
            <label v-for="(option, key) in options" :key="key" class="option">
                <input type="radio" :value="option" v-model="selectedAnswer" @change="selectAnswer">
                <span class="option-text">{{ key }}. {{ option }}</span>
            </label>
        </div>
    </div>
</template>

<script>
export default {
    name: 'questionItem',
    props: {

        data: {
            type: Object,
            required: true,
        },
        id: {
            type: Number,
            required: true,
        },
    },
    data() {
        return {
            question: this.data.question,
            options: {
                A: this.data.answer_A,
                B: this.data.answer_B,
                C: this.data.answer_C,
                D: this.data.answer_D
            },
            selectedAnswer: ''
        };
    },
    computed: {
        correctAnswer() {
            return this.data.trueAnswer;
        }
    },
    methods: {
        selectAnswer() {
            console.log("选择选项:", this.selectedAnswer)
            this.$emit('answer-selected', this.selectedAnswer);
        }
    }
};
</script>

<style scoped>
/* 这里是你的样式 */
/* .question-item {
  border: 1px solid #ccc;
  padding: 10px;
  margin-bottom: 20px;
}

.options {
  margin-top: 10px;
}

.option {
  margin-bottom: 5px;
}

.option input[type="radio"] {
  margin-right: 5px;
}

.option span {
  vertical-align: middle;
} */
.question-item {
    margin-bottom: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
    padding: 10px;
    background-color: #f9f9f9;
}

.question {
    margin-bottom: 10px;
}

.options {
    display: flex;
    flex-direction: column;
}

.option {
    margin-bottom: 5px;
}

.option-input {
    margin-right: 5px;
}

.option-text {
    font-size: 16px;
}
</style>