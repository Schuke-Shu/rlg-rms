import { createApp } from 'vue'
import { createPinia } from 'pinia'

import Rlg from './Rlg.vue'
import router from './router'

const rlg = createApp(Rlg)

rlg.use(createPinia())
rlg.use(router)

rlg.mount('#rlg')
