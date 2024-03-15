import axios from 'axios'
import router from '../router/index.js'



if (process.env.NODE_ENV === 'development') {
    axios.defaults.baseURL = 'http://127.0.0.1:8888'
} else if (process.env.NODE_ENV === 'production') {
    axios.defaults.baseURL = 'http://127.0.0.1:8888'
}

axios.defaults.timeout = 15000
axios.defaults.withCredentials = true;

// let loadingInstance1 = null;
axios.interceptors.request.use((config) => {
    let token = window.localStorage.getItem('token');
    if (token != null) {
        config.headers.auth = token;
    }
    return config;
}, (error) => {
    return Promise.reject(error);
});


axios.interceptors.response.use((response) => {
    if (response.status === 200) {
        let code = response.data.code;
        if ([403, 404, 405, 406, 407, 409, 410].includes(code)) {

            router.push('/');
        }

        return Promise.resolve(response);
    } else {
        return Promise.reject(response);
    }
});
export default axios;
