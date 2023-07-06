// axios的基础封装
import axios from "axios";
import qs from "qs";
import env from '@utils/Const';

const send = axios.create({
    baseURL: env.baseUrl,
    timeout: 60000
});

send.interceptors.request.use(function (config) {
    const user = qs.parse(
        localStorage.getItem(env.storageKeyUser)
    )
    user && (config.headers[env.requestHeaderAuthorization] = user.token);
    return config;
})

const web = function (config, success, fail)
{
    send(config)
        .then(
            res => {
                let data = res.data;

                switch (data.status) {
                    case 20000:
                        success && success(data.data);
                        break;
                    case 60100:
                    case 60200:
                    case 60300:
                        localStorage.removeItem(env.storageKeyUser);
                        location.href = '/';
                        break;
                    default:
                        fail && fail(data);
                }
            }
        );
}

export default web;