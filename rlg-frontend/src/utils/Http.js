// axios的基础封装
import axios from "axios";
import qs from "qs";
import VAR from '@utils/Const';

const send = axios.create({
    baseURL: VAR.baseUrl,
    timeout: 60000
});

send.interceptors.request.use(function (config) {
    const user = qs.parse(
        localStorage.getItem(VAR.storageKeyUser)
    )
    user && (config.headers[VAR.requestHeaderAuthorization] = user.jwt);
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
                        localStorage.removeItem(VAR.storageKeyUser);
                        location.href = '/';
                        break;
                    default:
                        console.log(typeof data.status)
                        fail && fail(data);
                }
            }
        );
}

export default web;