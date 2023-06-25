import { axPost } from '@utils/http';

export function login(data)
{
    return axPost({
        url: 'user/login',
        data: data
    })
}