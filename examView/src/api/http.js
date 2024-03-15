// import axios from './api.js'
// import router from '../router/index.js'
/*
    在http.js中,将每一个axios请求都封装为1个方法
    parms就是调用该方法的时候需要传递的参数
*/

// 管理员请求
// const loginA = async (params) => {
//     const res = await axios({
//         headers: {
//             "Content-Type": "application/json;charset=UTF-8",
//         },
//         url: 'public/loginA',
//         method: 'post',
//         data: JSON.stringify(params),
//     });
//     return res.data;
// }

// // 学生请求
// const loginS = async (params) => {
//     const res = await axios({
//         headers: {
//             "Content-Type": "application/json;charset=UTF-8",
//         },
//         url: `public/loginS/${params}`,
//         method: 'get',
//         data: JSON.stringify(params),
//     });
//     return res.data;
// }
// // 教师请求
// const loginT = async (params) => {
//     const res = await axios({
//         headers: {
//             "Content-Type": "application/json;charset=UTF-8",
//         },
//         url: `public/loginT/${params}`,
//         method: 'get',
//         data: JSON.stringify(params),
//     });
//     return res.data;
// }

// let jwt = "";

function ajaxGet(url, token = "") {
    return new Promise((res, rej) => {
        let xhr = new XMLHttpRequest();
        xhr.open("GET", url);
        xhr.setRequestHeader("token", token);
        xhr.send();
        xhr.onload = function () {
            res(xhr.responseText);
            rej(xhr.statusText);
        }
    })
}


function ajaxPost(url, token = "", data = {}) {
    return new Promise((res, rej) => {
        let xhr = new XMLHttpRequest();
        xhr.open("POST", url);
        xhr.setRequestHeader("token", token);
        xhr.setRequestHeader("Content-type", "application/json");
        xhr.send(JSON.stringify(data));
        xhr.onload = function () {
            res(xhr.responseText);
            rej(xhr.statusText);
        }
    })
}

// async function loginS() {
//     let res = await ajaxGet("http://localhost:8080/public/loginS/202119120101");
//     console.log(JSON.parse(res));
//     let data = JSON.parse(res);
//     console.log(data);
//     console.log(data.data.jwt);
//     if (jwt == "") {
//         jwt = data.data.jwt;
//     }
//     localStorage.setItem("jwt", data.data.jwt);
//     console.log(jwt);
// }

// async function findExam() {
//     jwt = localStorage.getItem("jwt");
//     console.log("jwt: " + jwt);
//     let res = await ajaxGet("http://localhost:8080/student/apply?sid=202119120101&exaId=15", jwt);
//     let data = JSON.parse(res);
//     console.log(data);
// }

export default {
    ajaxGet, ajaxPost,
    // loginA, loginS, loginT
}
