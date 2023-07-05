import web from '@utils/Http';
import qs from "qs";
import * as jose from 'jose';
import VAR from "@utils/Const";

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
            headers: {[VAR.requestHeaderLoginWay]: way}
        },
        data => {
            let info = jose.decodeJwt(data);
            let user = {
                info: info,
                jwt: data
            }
            localStorage.setItem(VAR.storageKeyUser, qs.stringify(user));
            location.reload();
        },
        data => alert(data.message)
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
        },
        data => alert(data.message)
    )
}

export function getCode(uri, account)
{
    web(
        {
            method: 'GET',
            url: '/code' + uri + '?account=' + account
        },
        null,
        data => alert(data.message)
    )
}