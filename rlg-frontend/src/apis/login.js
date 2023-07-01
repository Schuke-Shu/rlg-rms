import {axGet, axPost} from '@utils/http';
import qs from "qs";
let data;

export function login(form)
{
    axPost({
        url: '/user/login',
        data: qs.stringify(form)
    }).then(res => {
        data = res.data;
        if (data.status == 20000)
        {
            localStorage.setItem('user', res.data.data)
            location.reload();
        }
        else
            alert(data.message)
    })
}

export function emailLogin(form)
{
    axPost({
        url: '/user/email-login',
        data: qs.stringify(form)
    }).then(res => {
        data = res.data;
        if (data.status == 20000)
        {
            localStorage.setItem('user', res.data.data)
            location.reload();
        }
        else
            alert(data.message)
    })
}

export function getCode(email)
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
        data = res.data;
        if (data.status == 20000)
            alert("注册成功！");
        else
            alert(data.message)
    })
}

export async function getUserInfo()
{
    let userInfo;
    let promise = await axGet({
        url: '/user/user-info',
        headers: {
            Authorization: localStorage.getItem('user')
        }
    });
    userInfo = promise.data.data;
    return userInfo;
}