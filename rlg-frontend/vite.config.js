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
      resolvers: [
          // 配置elementPlus采用scss样式配色系统
          ElementPlusResolver({ importStyle: "sass" }),
      ],
    }),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
      '@mrc': fileURLToPath(new URL('./src/components/mr', import.meta.url)),
      '@rlg': fileURLToPath(new URL('./src/components/rlg', import.meta.url)),
      '@apis': fileURLToPath(new URL('./src/apis', import.meta.url)),
      '@assets': fileURLToPath(new URL('./src/assets', import.meta.url)),
      '@stores': fileURLToPath(new URL('./src/stores', import.meta.url)),
      '@styles': fileURLToPath(new URL('./src/styles', import.meta.url)),
      '@utils': fileURLToPath(new URL('./src/utils', import.meta.url)),
      '@views': fileURLToPath(new URL('./src/views', import.meta.url)),
    }
  },
  css: {
    preprocessorOptions: {
      scss: {
        // 自动导入定制化样式文件进行样式覆盖
        additionalData: `
          @use "@styles/element/index.scss" as *;
        `,
      }
    }
  }
})