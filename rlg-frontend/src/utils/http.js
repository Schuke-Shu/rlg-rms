// axios的基础封装
import axios from "axios";

export const axGet = axios.create({
    method: 'GET',
    baseURL: 'http://localhost:10086',
    timeout: 60000
});

export const axPost = axios.create({
    method: 'POST',
    baseURL: 'http://localhost:10086',
    timeout: 60000
});