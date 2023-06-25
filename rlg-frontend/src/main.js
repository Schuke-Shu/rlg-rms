import { createApp } from 'vue';
import { createPinia } from 'pinia';

import Rlg from './Rlg.vue';
import router from './router';

const rlg = createApp(Rlg);
const pinia = createPinia();

rlg.use(pinia);
rlg.use(router);

rlg.mount('#rlg');