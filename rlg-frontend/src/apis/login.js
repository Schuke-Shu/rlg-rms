import {axGet, axPost} from '@utils/http';
import qs from "qs";
import * as jose from 'jose';

export function loginByPassword(form)
{
    axPost({
        url: '/user/login',
        data: qs.stringify(form),
        headers: {
            "login-way": 'pwd'
        }
    }).then(res => getInfo(res.data))
}

export function emailLogin(form)
{
    axPost({
        url: '/user/email-login',
        data: qs.stringify(form)
    }).then(res => getInfo(res.data))
}

export function getEmailLoginCode(email)
{
    axGet({
        url: '/user/email-login?email=' + email
    }).then(res => {
        if (res.data.status != 20000)
            alert(res.data.message)
    })
}

export function register(form)
{
    axPost({
        url: '/user/register',
        data: qs.stringify(form)
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