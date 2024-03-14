import { createStore } from 'vuex'


export default createStore({
  state: {
    //教师菜单****************************************************************************
    menu_teacher: [
      {
        id: "1",
        title: '考试管理',
        router: '',
        icon: 'UserFilled',
        SubClass: [
          {
            id: "1-1",
            title: '查看考试',
            router: '/addExam',
          },
          {
            id: "1-2",
            title: '查看考试',
            router: '/myExam',
          },
          {
            id: "1-3",
            title: '查看缺考数据',
            router: '/loseStudents',
          },

        ],
      },
      {
        id: "2",
        title: '题库管理',
        router: '',
        icon: 'Document',
        SubClass: [
          {
            id: "2-1",
            title: '添加题库',
            router: '',
          },
        ],
      },
      {
        id: "3",
        title: '成绩管理',
        router: '',
        icon: 'EditPen',
        SubClass: [
          {
            id: "3-1",
            title: '查询成绩',
            router: '/allStudent',
          },
        ],

      },
      {
        id: "4",
        title: '学生管理',
        router: '',
        icon: 'Setting',
        SubClass: [
          {
            id: "4-1",
            title: '查看学生',
            router: '/studentManage',
          },
          {
            id: "4-2",
            title: '添加学生',
            router: '/addStudent',
          },
        ],
      },
    ],
    //学生菜单*****************************************************************************
    menu_student: [
      {
        id: "1",
        title: '考试管理',
        router: '',
        icon: 'UserFilled',
        SubClass: [
          {
            id: "1-1",
            title: '查看考试',
            router: '/myExam',
          },

        ],
      },
      {
        id: "2",
        title: '成绩管理',
        router: '',
        icon: 'EditPen',
        SubClass: [
          {
            id: "2-1",
            title: '查看成绩',
            router: '/myScore',
          },

        ],
      },
    ],
    menu_admin: [

    ],
  },
  getters: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
  }
})
