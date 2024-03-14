import { createRouter, createWebHashHistory } from 'vue-router'



const routes = [
  {
    path: '/',
    redirect: '/LoginView',
  },
  {
    path: '/LoginView',
    // name: 'loginView',
    component: () => import('@/views/LoginView.vue'),
  },
  //主界面：
  {
    path: '/Index',
    name: 'index',
    component: () => import('@/views/ViewIndex.vue'),
    children: [
      //添加学生
      {
        path: '/addStudent',
        component: () => import('../components/teacher/AddStudent.vue'),
      },
      //查看缺考
      {
        path: '/loseStudents',
        component: () => import('../components/admin/LoseStudents.vue'),
      },
      //添加考试
      {
        path: '/addExam',
        component: () => import('../components/teacher/addExam.vue'),
      },
      //查看考试
      {
        path: '/myExam',
        component: () => import('../components/teacher/myExam.vue'),
      },
      //学生管理
      {
        path: '/studentManage',
        component: () => import('../components/teacher/LookStudent.vue'),
      },
      //查看所有学生成绩
      {
        path: '/allStudent',
        component: () => import('../components/teacher/allStudentsGrade.vue'),
      },
      //添加题库
      {
        path: '/addAnswer',
        component: () => import('../components/admin/addAnswer.vue'),
      },
      //学生成绩
      {
        path: '/myScore',
        component: () => import('../components/student/scoreTable.vue')
      },
    ],
  },

]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if ((to.path === '/student' || to.path === '/index') && !token) {
    next('/login')
  } else {
    next()
  }
})
export default router
