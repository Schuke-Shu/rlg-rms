<style scoped>
#nav
{
    /*border: 1px red solid;*/
    position: absolute;
    top: 0;
    width: 100vw;
    height: 4.5rem;
    backdrop-filter: blur(6px);
    background-color: rgba(0, 0, 0, 0.1);
    box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.1);
}

li > a
{
    transition: 300ms;
}

#nav_menu li > a:hover
{
    color: rgba(208, 208, 208);
}

#nav_menu li
{
    float: left;
}

#nav_menu li + li
{
    margin-left: 2rem;
}

#nav_menu li span
{
    margin-left: .3rem;
    position: relative;
    bottom: .2rem
}

.el-icon
{
    font-size: 1.2rem;
}

.el-avatar
{
    float: right;
    position: relative;
    bottom: .5rem;
    right: 2rem;
}

#login_dialog
{
    width: 500px;
    margin: auto;
    padding: 20px 30px;
    background-color: #fff4df;
    border-radius: 10px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
    transition: all 0.3s ease;
}

.model-header h2 {
    color: #00d0d0;
}

.input_label
{
    position: relative;
    top: .3rem;
}

.input_row + .input_row
{
    margin-top: 1.5rem;
}

.login_change
{
    color: #ff9cb3;
}

.input_row span
{
    float: right;
    margin-right: 1.5rem;
}
</style>

<template>
    <el-row id="nav">
        <!-- logo -->
        <el-col :span="4" style="padding: 1.2rem 0">
            <a href="/">
                <span style="color: #ffeee9; margin-left: 2rem; font-size: 1.8rem; font-family: 'Rlg Title', sans-serif">红叶园</span>
                <span style="font-size: 1.4rem">🍁</span>
            </a>
        </el-col>

        <el-col v-if="hasSignedIn" :span="12" :offset="8" style="padding: 1.5rem 0">
            <el-row>
                <!-- 导航菜单 -->
                <el-col :span="20">
                    <ul id="nav_menu">
                        <li>
                            <a href="/">
                                <el-icon>
                                    <home-filled />
                                </el-icon>
                                <span>主页</span>
                            </a>
                        </li>
                        <li>
                            <a href="/disk">
                                <el-icon>
                                    <upload-filled />
                                </el-icon>
                                <span>云盘</span>
                            </a>
                        </li>
                    </ul>
                </el-col>

                <!-- 头像 -->
                <el-col :span="4">
                    <el-popover
                            popper-style="box-shadow: rgb(14 18 22 / 35%) 0px 10px 38px -10px, rgb(14 18 22 / 20%) 0px 10px 20px -15px; padding: 20px;"
                    >
                        <template #reference>
                            <el-avatar :src="avatarUrl" />
                        </template>
                        <template #default>
                            <div>
                                <a href="javascript:void(0)" style="color: black" @click="outLogin">退出登录</a>
                            </div>
                        </template>
                    </el-popover>
                </el-col>
            </el-row>
        </el-col>
        <el-col v-else :span="2" :offset="18" style="padding: 1.2rem">
            <el-button id="show_login_dialog_button" @click="loginDialogVisible = true">登录/注册</el-button>
        </el-col>
    </el-row>

    <!-- 模态框 -->
    <Dialog v-model="loginDialogVisible" v-if="!hasSignedIn">
        <template #header>
            <h2 v-if="isLogin">Login</h2>
            <h2 v-else>Register</h2>
        </template>

        <template #body>
            <div v-if="isLogin">
                <el-row class="input_row">
                    <el-col :span="6">
                        <span class="input_label">账户：</span>
                    </el-col>
                    <el-col :span="17">
                        <el-input v-model="account" :placeholder="placeholder" />
                        <a @click="getLoginCode(account)" href="javascript:void(0)" v-if="!loginByPassword" style="color: #00d0d0; position: relative; left: .2rem; top: .3rem">获取验证码</a>
                    </el-col>
                </el-row>
                <el-row class="input_row">
                    <el-col :span="6">
                        <span class="input_label">{{ keyWord }}：</span>
                    </el-col>
                    <el-col :span="17">
                        <el-input v-model="password" v-if="loginByPassword" placeholder="密码" />
                        <el-input v-model="code" v-else placeholder="验证码" />
                    </el-col>
                </el-row>
                <el-row style="margin-top: 1rem">
                    <el-col :span="6">
                        <a class="login_change" href="javascript:void(0)" @click="loginByPassword = !loginByPassword">
                            <el-icon>
                                <right />
                            </el-icon>
                            <span style="margin-left: .5rem; position: relative; bottom: .25rem">{{ toKeyWord }}登录</span>
                        </a>
                    </el-col>
                    <el-col :span="8" :offset="9">
                        <span style="position: relative; bottom: .15rem">还没有账号？</span>
                        <a href="javascript:void(0)" @click="isLogin = !isLogin" style="color: #ff9cb3; position: relative; bottom: .15rem">去注册</a>
                    </el-col>
                </el-row>
            </div>

            <div v-else>
                <el-row class="input_row">
                    <el-col :span="6">
                        <span class="input_label">用户名：</span>
                    </el-col>
                    <el-col :span="17">
                        <el-input v-model="account" placeholder="用户名" />
                    </el-col>
                </el-row>
                <el-row class="input_row">
                    <el-col :span="6">
                        <span class="input_label">密码：</span>
                    </el-col>
                    <el-col :span="17">
                        <el-input v-model="password" placeholder="密码"/>
                    </el-col>
                </el-row>
                <el-row style="margin-top: 1rem">
                    <el-col :span="8" :offset="15">
                        <span style="position: relative; bottom: .15rem">已有账号？</span>
                        <a href="javascript:void(0)" @click="loginByPassword = true; isLogin = !isLogin" style="color: #ff9cb3; position: relative; bottom: .15rem">去登录</a>
                    </el-col>
                </el-row>
            </div>
        </template>

        <template #footer>
            <el-row>
                <el-col :span="4" :offset="15">
                    <el-button type="info" plain @click="loginDialogVisible = !loginDialogVisible">取消</el-button>
                </el-col>
                <el-col :span="4" v-if="isLogin">
                    <el-button type="primary" plain @click="loginByPassword ? login(account, password, passwordLogin) : login(account, code, codeLogin)">登录</el-button>
                </el-col>
                <el-col :span="4" v-else>
                    <el-button type="primary" plain @click="register(account, password)">注册</el-button>
                </el-col>
            </el-row>
        </template>
    </Dialog>
</template>

<script setup>
import {computed, onMounted, ref} from "vue";
import {HomeFilled, Right, UploadFilled} from "@element-plus/icons-vue";
import Dialog from "@mrc/Dialog.vue";
import {getLoginCode, login, register} from "@apis/login";
import qs from "qs";

const avatarUrl = ref('/img/defaultAvatar.png');

const hasSignedIn = ref(false);
const loginDialogVisible = ref(true);
const loginByPassword = ref(true);
const isLogin = ref(true);

const account = ref('');
const password = ref('');
const code = ref('');

const passwordLogin = 'pwd';
const codeLogin = 'code';

const placeholder = computed(() => {
    if (loginByPassword.value)
        return "用户名/邮箱/手机号";
    else
        return "邮箱/手机号";
})

const keyWord = computed(() => {
    if (loginByPassword.value)
        return "密码";
    else
        return "验证码";
})

const toKeyWord = computed(() => {
    if (loginByPassword.value)
        return "验证码";
    else
        return "密码";
})

onMounted(() => {
    init();
})

async function init()
{

    let user = localStorage.getItem('user');
    if (user == null) return;
    hasSignedIn.value = true;
    user = qs.parse(user);
    let avatar = user.info.avatarUrl;
    avatarUrl.value = avatar == null ? avatarUrl.value : import.meta.env.VITE_BASE_URL + '/static/' + avatar;
}

const outLogin = function ()
{
    localStorage.removeItem('user');
    location.reload();
}

// const checkJwt = function (info)
// {
//     let timeout = new Date(info.timeout);
//     let now = new Date();
//     console.log(timeout.getTime() - now.getTime())
//
//     if (
//         (timeout.getTime() - now.getTime()) <
//         24 * 60 * 60 * 1000 // 一天
//     )
//         console.log(123);
// }
</script>