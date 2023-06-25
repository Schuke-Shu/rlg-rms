<template>
  <div></div>
</template>

<script setup>
import {onMounted, ref} from "vue";
import { login } from '@apis/LoginApi';
import qs from "qs";

console.log("主页")

const user = ref(null);

onMounted(() => {
    let userinfo = {
        username: 'rlgroot',
        password: 'P@ssw0rdRoot'
    }
    let str = qs.stringify(userinfo);
    // let formData = new FormData();
    // formData.append('username', 'rlgroot');
    // formData.append('password', 'P@ssw0rdRoot');
    console.log(str);
    login(str).then(res => {
        console.log(res.data.data)
    });
})
</script>

<style scoped>

</style>