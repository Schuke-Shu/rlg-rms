import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    AutoImport({
      resolvers: [ElementPlusResolver()],
    }),
    Components({
      resolvers: [ElementPlusResolver()],
    }),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
      '@mrc': fileURLToPath(new URL('./src/components/mr', import.meta.url)),
      '@rlg': fileURLToPath(new URL('./src/components/rlg', import.meta.url)),
      '@api': fileURLToPath(new URL('./src/apis', import.meta.url)),
      '@as': fileURLToPath(new URL('./src/assets', import.meta.url)),
      '@st': fileURLToPath(new URL('./src/styles', import.meta.url)),
      '@ut': fileURLToPath(new URL('./src/utils', import.meta.url)),
      '@vw': fileURLToPath(new URL('./src/views', import.meta.url)),
    }
  }
})
