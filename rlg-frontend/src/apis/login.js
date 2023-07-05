import {axGet, axPost} from '@utils/http';
import qs from "qs";
import * as jose from 'jose';

export function login(account, key, way)
{
    axPost({
        url: '/user/login',
        data: qs.stringify({
            account: account,
            key: key
        }),
        headers: {'login-way': way}
    }).then(res => getInfo(res.data))
}

export function getLoginCode(account)
{
    axGet({
        url: '/code/user/login?account=' + account
    }).then(res => {
        if (res.data.status != 20000)
            alert(res.data.message)
    })
}

export function register(username, password)
{
    axPost({
        url: '/user/register',
        data: qs.stringify({
            username: username,
            password: password
        })
    }).then(res => {
        let data = res.data;
        if (data.status == 20000)
            alert("注册成功！");
        else
            alert(data.message)
    })
}

function getInfo(data)
{
    if (data.status == 20000)
    {
        let jwt = data.data;
        let info = jose.decodeJwt(jwt);
        let user = {
            info: info,
            jwt: jwt
        }
        localStorage.setItem('user', qs.stringify(user));
        location.reload();
    }
    else
        alert(data.message)
}