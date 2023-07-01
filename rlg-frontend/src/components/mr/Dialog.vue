<style>
.model-mask {
    position: fixed;
    z-index: 1000;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    display: flex;
    transition: opacity 0.3s ease;
}

.model-container {
    width: 500px;
    margin: auto;
    padding: 20px 30px;
    background-color: #fff4df;
    border-radius: 10px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
    transition: all 0.3s ease;
}

.model-body {
    margin: 20px 0;
}

.model-enter-from {
    opacity: 0;
}

.model-leave-to {
    opacity: 0;
}

.model-enter-from .model-container,
.model-leave-to .model-container {
    -webkit-transform: scale(1.1);
    transform: scale(1.1);
}
</style>

<template>
    <Teleport to="body">
        <Transition name="model">
            <div v-if="value" class="model-mask" @click="value = false">
                <div class="model-container" @click.stop>
                    <div class="model-header">
                        <slot name="header">Title</slot>
                    </div>
                    <div class="model-body">
                        <slot name="body" />
                    </div>
                    <div class="model-footer">
                        <slot name="footer">
                            <button @click="value = false">ok</button>
                        </slot>
                    </div>
                </div>
            </div>
        </Transition>
    </Teleport>
</template>

<script setup>
import {computed} from "vue";

const props = defineProps({
    modelValue: Boolean,
})
const emit = defineEmits(['update:modelValue'])

const value = computed({
    get() {
        return props.modelValue
    },
    set(value) {
        emit('update:modelValue', value)
    }
})
</script>