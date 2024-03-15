<template>
    <div class="main">
        <div class="login-container">
            <div class="left-container">
                <div class="title"><span>登录</span></div>
                <div class="input-container">
                    <input type="text" v-model="user.username" name="username" placeholder="用户名">
                    <input type="password" v-model="user.password" name="password" placeholder="密码">
                </div>
                <div>
                    <select v-model="user.role">
                        <option value="student">学生</option>
                        <option value="teacher">教师</option>
                        <option value="admin">管理员</option>
                    </select>
                </div>
            </div>

            <div class="right-container">
                <div class="action-container" @click="tlogin()">
                    <span>提交</span>
                </div>
            </div>
        </div>
    </div>
</template>

<script>

export default {

    name: 'LoginView',
    props: {
        msg: String
    },
    data() {
        return {
            user: {
                username: '',
                password: '',
                role: 'student', // 默认角色为学生
            }
        }
    },
    methods: {
        tlogin() {
            console.log("当前：")
            console.log(this.user.role)
            console.log("user为:")
            console.log(this.user)
            if (this.user.role == 'student') {
                this.$axios.get(`public/loginS/${this.user.username}`).then(res => {
                    console.log("来了");
                    console.log(res.data)

                    console.log("信息:", res.data.message);
                    if (res.data.code == 200) {
                        this.$message.success(res.data.message);
                        console.log("打印:")
                        console.log(res.data.data.jwt);
                        localStorage.setItem('token', res.data.data.jwt);
                        let ans = res.data.data;
                        ans['role'] = this.user.role;
                        localStorage.setItem('user', JSON.stringify(ans));
                        this.$router.push('/Index');
                    }
                    else {
                        this.$message.error(res.data.message);
                    }
                });
            }
            else if (this.user.role == 'teacher') {
                this.$axios.get(`public/loginT/${this.user.username}`).then(res => {
                    console.log(res.data)

                    if (res.data.code == 200) {
                        this.$message.success(res.data.message);
                        localStorage.setItem('token', res.data.data.jwt);
                        let ans = res.data.data;
                        ans['role'] = this.user.role;
                        localStorage.setItem('user', JSON.stringify(ans));
                        this.$router.push('/Index');
                    }
                    else {
                        this.$message.error(res.data.message);
                    }
                });
            }
            else {
                this.$axios.post(`public/loginA`, this.user).then(res => {
                    console.log(res.data)

                    if (res.data.code == 200) {
                        this.$message.success(res.data.message);
                        localStorage.setItem('token', res.data.data.jwt);
                        let ans = res.data.data;
                        ans['role'] = this.user.role;
                        localStorage.setItem('user', JSON.stringify(ans));
                        this.$router.push('/Index');
                    }
                    else {
                        this.$message.error(res.data.message);
                    }
                });
            }
        },


    },
}
</script>


<style scoped>
.main {
    height: 100%;
    width: 100%;
    background-color: azure;
    overflow: hidden;
}

.login-container {
    width: 600px;
    height: 315px;
    margin: 0 auto;
    margin-top: 10%;
    border-radius: 15px;
    box-shadow: 1px 1px 10px gray;
    background-color: rgb(95, 76, 194);
}

.left-container {
    display: inline-block;
    width: 330px;
    border-top-left-radius: 15px;
    border-bottom-left-radius: 15px;
    padding: 60px;
    background-image: linear-gradient(to bottom right, rgb(118, 76, 163), rgb(92, 103, 211));
}

.title {
    color: #fff;
    font-size: 18px;
    font-weight: 200;
}

.title span {
    border-bottom: 3px solid rgb(237, 221, 22);
}

.input-container {
    padding: 20px 0;
}

input {
    border: 0;
    background: none;
    outline: none;
    color: #fff;
    margin: 20px 0;
    display: block;
    width: 100%;
    padding: 5px 0;
    transition: .2s;
    border-bottom: 1px solid rgb(199, 191, 219);
}

input:hover {
    border-bottom-color: #fff;
}

::-webkit-input-placeholder {
    color: rgb(199, 191, 219);
}

.message-container {
    font-size: 14px;
    transition: .2s;
    color: rgb(199, 191, 219);
    cursor: pointer;
}

.message-container:hover {
    color: #fff;
}

.right-container {
    width: 145px;
    display: inline-block;
    height: calc(100% - 120px);
    vertical-align: top;
    padding: 60px 0;
    box-shadow: -1px 0 4px lightgray;
}

.regist-container {
    text-align: center;
    color: #fff;
    font-size: 18px;
    font-weight: 200;
}

.regist-container span {
    border-bottom: 3px solid rgb(237, 221, 22);
}

.action-container {
    font-size: 10px;
    color: #fff;
    text-align: center;
    position: relative;
    top: 200px;
}

.action-container span {
    border: 1px solid rgb(237, 221, 22);
    padding: 10px;
    display: inline;
    line-height: 20px;
    border-radius: 20px;
    position: absolute;
    bottom: 10px;
    left: calc(72px - 20px);
    transition: .2s;
    cursor: pointer;
}

.action-container span:hover {
    background-color: rgb(237, 221, 22);
    color: rgb(95, 76, 194);
}
</style>
