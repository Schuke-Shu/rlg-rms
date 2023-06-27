<template>
  <div></div>
</template>

<script setup>
import {onMounted, ref} from "vue";
import { login } from '@apis/LoginApi';
import qs from "qs";

console.log("主页")

import * as jose from 'jose';

const user = ref(null);
onMounted(() => {

    const jwt = jose.decodeJwt("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwaG9uZSI6bnVsbCwiaXAiOiIwOjA6MDowOjA6MDowOjEiLCJleHAiOjE2ODg0NjM2MjcsInV1aWQiOiIxNTFkM2Q1NDAzMTE0MDAxOTIzN2FmMzVkODc0YjRmZCIsImVtYWlsIjoiMTQzMzI3NTkwNEBxcS5jb20iLCJhdXRob3JpdGllcyI6IltdIiwidXNlcm5hbWUiOiJybGdyb290In0.rassKNXCkkJk9GGSyQlPrmz4aaFOWA370_2SVXnP3hA");
    console.log(jwt);
})
</script>

<style scoped>

</style>