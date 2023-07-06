<style scoped>
  .el-container
  {
      border-radius: .8rem;
      width: 90vw;
      height: 89vh;
      margin-left: auto;
      margin-right: auto;
      margin-top: 5.5rem;
      background-color: rgba(255, 248, 234, 0.8);
      overflow: hidden;
  }

  .main-enter-active,
  .main-leave-active {
      transition: opacity 1s ease;
  }

  .main-enter-from,
  .main-leave-to {
      opacity: 0;
  }
</style>

<template>
    <transition name="main">
        <el-container v-if="show">
            <el-aside>
                <el-menu
                    default-active="1-1"
                    active-text-color="#00c0c0"
                    background-color="rgba(253, 229, 182, 0.2)"
                    style="height: 100%"
                >
                    <el-sub-menu index="1">
                        <template #title>
                            <el-icon><files /></el-icon>
                            <span>我的文件</span>
                        </template>
                        <el-menu-item index="1-1">
                            <el-icon><more /></el-icon>
                            <span>全部</span>
                        </el-menu-item>
                        <el-menu-item index="1-2">
                            <el-icon><pic /></el-icon>
                            <span>图片</span>
                        </el-menu-item>
                        <el-menu-item index="1-3">
                            <el-icon><video-play /></el-icon>
                            <span>视频</span>
                        </el-menu-item>
                        <el-menu-item index="1-4">
                            <el-icon><headset /></el-icon>
                            <span>音乐</span>
                        </el-menu-item>
                        <el-menu-item index="1-5">
                            <el-icon><document /></el-icon>
                            <span>文档</span>
                        </el-menu-item>
                    </el-sub-menu>
                    <el-menu-item index="2">
                        <el-icon><delete /></el-icon>
                        <span>回收站</span>
                    </el-menu-item>
                </el-menu>
            </el-aside>
        </el-container>
    </transition>
</template>

<script setup>
import {onMounted, ref} from "vue";
import {
    Document,
    Files,
    Delete,
    Headset,
    Picture as Pic,
    VideoPlay, More,
} from '@element-plus/icons-vue'

  const show = ref(false);
  onMounted(() => {
      show.value = true;
  })
</script>