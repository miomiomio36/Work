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
      //管理员：
      //安排考试
      {
        path: '/adminAuExam',
        component: () => import('../components/admin/adminAuExam.vue'),
      },
      //缓考模块
      {
        path: '/adminHuExam',
        component: () => import('../components/admin/adminHuExam.vue'),
      },
      //缺考模块
      {
        path: '/adminLoExam',
        component: () => import('../components/admin/adminLoExam.vue'),
      },

      // 教师：
      // 教师考试模块
      {
        path: '/teacherExam',
        component: () => import('../components/teacher/teacherExam.vue'),
      },
      // 教师成绩模块
      {
        path: '/teacherGrade',
        component: () => import('../components/teacher/teacherGrade.vue'),
      },
      // 教师题库模块
      {
        path: '/teacherTiku',
        component: () => import('../components/teacher/teacherTiku.vue'),
      },
      // 学生
      // 学生考试模块：
      {
        path: '/studentExam',
        component: () => import('../components/student/studentExam.vue'),
      },

      // 学生成绩模块：
      {
        path: '/studentGrade',
        component: () => import('../components/student/studentGrade.vue'),
      },
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
        component: () => import('../components/teacher/studentManage.vue'),
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
  //答题界面：
  {
    path: '/Exam',
    component: () => import('@/views/ExamView.vue'),
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
