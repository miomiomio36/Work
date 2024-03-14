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
            router: '/teacherExam',
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
            title: '查询成绩',
            router: '/teacherGrade',
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
            router: '/studentExam',
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
            router: '/studentGrade',
          },

        ],
      },
    ],
    menu_admin: [
      {
        id: "1",
        title: '考试管理',
        router: '',
        icon: 'UserFilled',
        SubClass: [
          {
            id: "1-1",
            title: '安排考试',
            router: '/adminAuExam',
          },
          {
            id: "1-2",
            title: '查看缓考',
            router: '/adminHuExam',
            // router: '/studentManage',
          },
          {
            id: "1-3",
            title: '未考试科目与人数',
            router: '/adminLoExam',
          },
        ],
      },
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
