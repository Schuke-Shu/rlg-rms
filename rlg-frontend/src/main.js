import '@assets/css/base.css';
import '@assets/font/fonts.css';

import { createApp } from 'vue';
import { createPinia } from 'pinia';

import Rlg from './Rlg.vue';
import router from './router';
import * as ElementPlusIconsVue from '@element-plus/icons-vue';
import axios from "axios";
import * as jwt from 'jose';

const rlg = createApp(Rlg);

const pinia = createPinia();
// 这些属性用不到，只做提示用
let props = rlg.config.globalProperties;
props.$axios = axios;

props.$jwt = jwt;

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    rlg.component(key, component)
}

rlg
    .use(pinia)
    .use(router)
    .mount('#rlg');