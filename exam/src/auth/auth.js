// auth.js

// import { ref } from 'vue';

// // 保存token的响应式变量
// const token = ref(localStorage.getItem('token') || '');

// // 生成token的函数
// const generateToken = (userId) => {
//     const payload = {
//         userId,
//         exp: Math.floor(Date.now() / 1000) + (60 * 60) // 设置过期时间为1小时
//     };
//     const secretKey = 'your_secret_key';
//     const base64Header = Buffer.from(JSON.stringify({ alg: 'HS256', typ: 'JWT' })).toString('base64');
//     const base64Payload = Buffer.from(JSON.stringify(payload)).toString('base64');
//     const signature = require('crypto').createHmac('sha256', secretKey).update(base64Header + '.' + base64Payload).digest('base64');
//     const token = base64Header + '.' + base64Payload + '.' + signature;
//     return token;
// };

// // 验证token的函数
// const verifyToken = (token) => {
//     const [header, payload, signature] = token.split('.');
//     const secretKey = 'your_secret_key';
//     const expectedSignature = require('crypto').createHmac('sha256', secretKey).update(header + '.' + payload).digest('base64');
//     if (signature === expectedSignature) {
//         const decodedPayload = JSON.parse(Buffer.from(payload, 'base64').toString());
//         if (decodedPayload.exp && decodedPayload.exp > Math.floor(Date.now() / 1000)) {
//             return decodedPayload;
//         }
//     }
//     return null;
// };
// 登录函数，保存token到localStorage中


// // 退出登录函数，清除localStorage中的token
// const logout = () => {
//     localStorage.removeItem('token');
//     token.value = '';
// };
import axios from 'axios'
import router from '../router/index.js'

// const login = async (user) => {
//     if (user.role == 'student') {
//         const res = await axios({
//             url: `/public/loginS/${user.username}`,
//             method: 'get',
//         });
//     }
//     else if (user.role == 'teacher') {
//         const res = await axios({
//             url: `/public/loginT/${user.username}`,
//             method: 'get',
//         });
//     }
//     else {
//         const res = await axios({
//             url: `/public/loginA/${user.username}`,
//             method: 'post',
//         });
//     }
//     return res.data;
// }


// 登录
const login = async (user) => {
    try {
        var response
        if (user.role == 'student') {
            response = await axios.get('http://127.0.0.1:8888/public/loginS/' + user.username);
        }
        else if (user.role == 'teacher') {
            response = await axios.get('http://127.0.0.1:8888/public/loginT/' + user.username);
        }
        else {
            response = await axios.post('http://127.0.0.1:8888/public/loginA', user);
        }
        // 发起登录请求并获取token
        // 处理登录成功的逻辑
        if (response.data.code == 200) {

            console.log('Login successful:', response.data);
            let ans = response.data.data;
            ans['role'] = user.role;
            localStorage.setItem('user', JSON.stringify(ans));
            // 保存token
            localStorage.setItem("token", JSON.stringify(response.data));
            router.push('/Index');
        }
    } catch (error) {
        console.error('Login failed:', error);
    }
};



//获取数据
function GetUserData() {
    // 获取本地保存好了的user数据
    const savedUserData = localStorage.getItem('user');
    const user = JSON.parse(savedUserData);
    // console.log(user);


    // if (user.role == 'student') {
    //     const response = await axios.get('http://127.0.0.1:8888/public/loginS/' + user.username);
    //     var user2 = response.data.data;
    // }
    // else if (user.role == 'teacher') {
    //     const response = await axios.get('http://127.0.0.1:8888/public/loginT/' + user.username);
    //     var user2 = response.data.data;
    // } else {
    //     const response = await axios.post('http://127.0.0.1:8888/public/loginA', user);
    //     var user2 = response.data.data;
    // }
    return user;
};



export { GetUserData, login, };

// export { token, loginA, logout, generateToken, verifyToken };
