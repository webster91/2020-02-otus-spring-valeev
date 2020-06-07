import axios from 'axios';
import {SERVER_API_URL} from "./constants";


const TIMEOUT = 1 * 60 * 1000;
axios.defaults.timeout = TIMEOUT;
axios.defaults.baseURL = SERVER_API_URL;

const setupAxiosInterceptors = () => {
    const onRequestSuccess = config => {
        console.log("onRequestSuccess")
        return config;
    };
    const onResponseSuccess = response => response;
    const onResponseError = err => {
        console.log("onRequestSuccess" + err)
        if (err.status === 404) {
            history.push('');
        }

        return Promise.reject(err);
    };
    axios.interceptors.request.use(onRequestSuccess);
    axios.interceptors.response.use(onResponseSuccess, onResponseError);
};

export default setupAxiosInterceptors;
