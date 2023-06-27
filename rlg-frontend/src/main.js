import { createApp } from 'vue';
import { createPinia } from 'pinia';

import Rlg from './Rlg.vue';
import router from './router';
import axios from "axios";
import * as jwt from 'jose';

const rlg = createApp(Rlg);
const pinia = createPinia();

// 这些属性用不到，只做提示用
let props = rlg.config.globalProperties;
props.$axios = axios;
props.$jwt = jwt;

rlg
    .use(pinia)
    .use(router)
    .mount('#rlg');