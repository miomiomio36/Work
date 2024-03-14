<template>
    <div>
        <!-- 在视图中使用获取到的数据 -->
        <div v-if="grades.length > 0">
          <h2>Student Grades</h2>
          <ul>
            <li v-for="grade in grades" :key="grade.id">{{ grade.subject }}: {{ grade.score }}</li>
          </ul>
        </div>
        <div v-else>
          <p>Loading...</p>
        </div>
      </div>
</template>




<script>
import { ref, onMounted } from 'vue';
import axios from 'axios';
export default {
    setup() {
        // 使用 ref 创建响应式变量
        const grades = ref([]);

        // 使用 onMounted 钩子，在组件挂载后调用获取数据的方法
        onMounted(fetchGradesData);

        async function fetchGradesData() {
            try {
                // 使用 Axios 发起 GET 请求
                const response = await axios.get('http://127.0.0.1:8888/student/grade');

                // 更新响应式变量
                grades.value = response.data;
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        }
        // 返回响应式变量和方法
        return {
            grades,
            fetchGradesData,
        }
    }
}

</script>




<style scoped>

</style>