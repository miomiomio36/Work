<!--学生管理页面-->
<template>
  <div class="all">
    <el-table :data="pagination.records" :default-sort="{ prop: 'grade', order: 'descending' }" border>
      <el-table-column fixed="left" prop="studentId" label="学号" width="180"></el-table-column>
      <el-table-column prop="studentName" label="姓名" width="100"></el-table-column>
      <el-table-column prop="clazz" label="班级" width="100"></el-table-column>
      <!-- <el-table-column prop="gender" label="性别" width="100"></el-table-column> -->
      <el-table-column prop="gender" label="性别" width="100">
        <template v-slot="scope">
          {{ scope.row.gender | formatGender }}
        </template>
      </el-table-column>

      <el-table-column prop="age" label="年龄" width="100"></el-table-column>
      <el-table-column prop="identityId" label="身份证号" width="240"></el-table-column>
      <el-table-column label="操作" width="150">
        <template v-slot="scope">
          <el-button @click="checkGrade(scope.row.studentId)" type="primary" size="small">编辑</el-button>
          <el-button @click="deleteById(scope.row.studentId)" type="danger" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
      v-model:currentPage="pagination.current" :page-sizes="[10, 20, 40, 80]" v-model:page-size="pagination.size"
      layout="total, sizes, prev, pager, next, jumper" :total="pagination.total" class="page">
    </el-pagination>
    <!-- 编辑对话框-->
    <el-dialog title="编辑学生信息" v-model="dialogVisible" width="30%">
      <section class="update">
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="学号">
            <el-input v-model="form.studentId"></el-input>
          </el-form-item>
          <el-form-item label="姓名">
            <el-input v-model="form.studentName"></el-input>
          </el-form-item>
          <el-form-item label="班级">
            <el-input v-model="form.clazz"></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-input v-model="form.gender"></el-input>
          </el-form-item>
          <el-form-item label="年龄">
            <el-input v-model="form.age"></el-input>
          </el-form-item>
          <el-form-item label="身份证号">
            <el-input v-model="form.identityId"></el-input>
          </el-form-item>
        </el-form>
      </section>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { GetUserData } from '../../auth/auth.js';
export default {
  name: "studentManage",
  data() {
    return {
      pagination: {
        current: 1,
        total: 0,
        size: 10
      },
      form: {},
      user: {},
      dialogVisible: false,
      students: [ // 添加示例学生数据
        { studentId: '001', studentName: '张三', clazz: 'Class A', gender: 'm', age: 20, identityId: 'xxxxxxxxxxxxxx' },
        { studentId: '002', studentName: '李四', clazz: 'Class B', gender: 'f', age: 22, identityId: 'yyyyyyyyyyyyyy' },
        { studentId: '001', studentName: '张三', clazz: 'Class A', gender: 'm', age: 20, identityId: 'xxxxxxxxxxxxxx' },
        { studentId: '002', studentName: '李四', clazz: 'Class B', gender: 'f', age: 22, identityId: 'yyyyyyyyyyyyyy' },
        { studentId: '001', studentName: '张三', clazz: 'Class A', gender: 'm', age: 20, identityId: 'xxxxxxxxxxxxxx' },
        { studentId: '002', studentName: '李四', clazz: 'Class B', gender: 'f', age: 22, identityId: 'yyyyyyyyyyyyyy' },
        { studentId: '001', studentName: '张三', clazz: 'Class A', gender: 'm', age: 20, identityId: 'xxxxxxxxxxxxxx' },
        { studentId: '002', studentName: '李四', clazz: 'Class B', gender: 'f', age: 22, identityId: 'yyyyyyyyyyyyyy' },
        { studentId: '001', studentName: '张三', clazz: 'Class A', gender: 'm', age: 20, identityId: 'xxxxxxxxxxxxxx' },
        { studentId: '001', studentName: '张三', clazz: 'Class A', gender: 'm', age: 20, identityId: 'xxxxxxxxxxxxxx' },
        { studentId: '002', studentName: '李四', clazz: 'Class B', gender: 'f', age: 22, identityId: 'yyyyyyyyyyyyyy' },
        { studentId: '001', studentName: '张三', clazz: 'Class A', gender: 'm', age: 20, identityId: 'xxxxxxxxxxxxxx' },
      ]
    }
  },
   created() {
    this.user = GetUserData()
    this.getStudentInfo()
  },
  //过滤器
  filters: {
    formatGender(value) {
      return value === 'f' ? '女' : (value === 'm' ? '男' : '');
    }
  },
  methods: {
    // 获取学生信息
    getStudentInfo() {
      // this.pagination.records = this.students; // 修改为使用示例学生数据
      // 模拟从后端获取数据
      // 这里应该根据后端 API 的实际情况进行修改
      this.pagination.total = this.students.length; // 设置总记录数为学生数组的长度
      const startIndex = (this.pagination.current - 1) * this.pagination.size;
      const endIndex = Math.min(startIndex + this.pagination.size, this.students.length); // 使用 Math.min() 限制结束索引不超过数组长度
      this.pagination.records = this.students.slice(startIndex, endIndex); // 分页获取学生数据


      const token = localStorage.getItem('token');
      const Id = this.user.tid;
      console.log("ID数据:",Id)
      console.log("跑一跑")
      console.log("token:");
      console.log(token);
      this.$http.ajaxGet(`http://localhost:8888/teacher/find/grade?tid=${Id}}`, token).then(res => {
        let response = JSON.parse(res);
        console.log(response);
      });
      // this.$axios.get('api/students/' + this.pagination.current + '/' + this.pagination.size)
      //     .then( res => {
      //       this.pagination = res.data.data
      //     }).catch( () => {
      //       this.$message.error('error')
      // })
    },
    //改变当前记录条数
    handleSizeChange(val) {
      this.pagination.size = val;
      this.getStudentInfo();
    },
    //改变当前页码，重新发送请求
    handleCurrentChange(val) {
      this.pagination.current = val;
      this.getStudentInfo();
    },
    // 修改学生信息
    checkGrade(studentId) {
      this.dialogVisible = true
      this.$axios.get('api/student/' + studentId).then(res => {
        this.form = res.data.data
      }).catch(() => {
        this.$message.error('error')
      })
    },
    // 提交修改后的学生信息
    submit() {
      this.dialogVisible = false
      this.$axios.put('api/student', { ...this.form }).then(res => {
        if (res.data.code == 200) {
          this.$message.success("修改成功！")
        }
      }).catch(() => {
        this.$message.error('error')
      })
      // 更新后重新获取学生信息
      this.getStudentInfo()
    },
    // 删除当前学生
    deleteById(studentId) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 成功删除
        this.$axios.delete('api/student/' + studentId).then(res => {
          if (res.data.code == 200) {
            this.$message.success("删除成功！")
          }
          this.getStudentInfo()
        })
      }).catch(() => {
      })
    }
  },
 
}
</script>

<style scoped>
.all {
  padding: 0px 40px;

  .page {
    margin-top: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .edit {
    margin-left: 20px;
  }

  .el-table tr {
    background-color: #dd5862 !important;
  }
}

.el-table .warning-row {
  background: #000 !important;
}

.el-table .success-row {
  background: #dd5862;
}
</style>
