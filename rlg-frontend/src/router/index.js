import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '@views/HomeView.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/disk',
      name: 'disk',
      component: () => import('@views/disk/CloudDiskView.vue')
    },
    {
      path: '/404',
      name: 'Not Found',
      component: () => import('@views/404.vue')

    },
    {
      path: '/:pathMatch(.*)*',
      redirect: '/404'
    }
  ]
})

export default router;
