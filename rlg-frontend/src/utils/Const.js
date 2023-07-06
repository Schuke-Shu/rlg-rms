const env = {
    // 后端地址
    baseUrl: import.meta.env.VITE_BASE_URL,
    // 存储在localStorage中的user的key
    storageKeyUser: import.meta.env.VITE_STORAGE_KEY_USER,
    // 请求头authorization
    requestHeaderAuthorization: import.meta.env.VITE_REQUEST_HEADER_AUTHORIZATION,
    // 请求头login-way
    requestHeaderLoginWay: import.meta.env.VITE_REQUEST_HEADER_LOGIN_WAY,
}

export default env;