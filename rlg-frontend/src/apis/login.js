import web from '@utils/Http';
import qs from "qs";
import * as jose from 'jose';
import env from "@utils/Const";
import {ElMessage} from "element-plus";

export function login(account, key, way)
{
    web(
        {
            method: 'POST',
            url: '/user/login',
            data: qs.stringify({
                account: account,
                key: key
            }),
            headers: {[env.requestHeaderLoginWay]: way}
        },
        data => {
            let info = jose.decodeJwt(data);
            let user = {
                info: info,
                token: data
            }
            localStorage.setItem(env.storageKeyUser, qs.stringify(user));
            location.reload();
        }
    );
}

export function register(username, password)
{
    web(
        {
            method: 'POST',
            url: '/user/register',
            data: qs.stringify({
                username: username,
                password: password
            })
        },
        () => {
            alert("注册成功！");
            location.href = '/'
        }
    )
}

export function getCode(uri, account)
{
    web(
        {
            method: 'GET',
            url: '/code' + uri + '?account=' + account
        }
    )
}

export function refreshJwt()
{
    web(
        {
            method: 'GET',
            url: '/user/token/refresh'
        },
        data => {
            let user = localStorage.getItem(env.storageKeyUser);
            user['token'] = data;
            localStorage.setItem(env.storageKeyUser, user);
        }
    )
}