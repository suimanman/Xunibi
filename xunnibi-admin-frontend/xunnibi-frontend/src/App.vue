<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
import Index from './index.vue'
export default {
  name: 'App',
  components: {
    Index
  },
  data() {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  }


}



// app.vue写在script里面  main.js写在app挂在完之后
const debounce = (fn, delay) => {
  let timer
  return (...args) => {
    if (timer) {
      clearTimeout(timer)
    }
    timer = setTimeout(() => {
      fn(...args)
    }, delay)
  }
}

const _ResizeObserver = window.ResizeObserver;
window.ResizeObserver = class ResizeObserver extends _ResizeObserver {
  constructor(callback) {
    callback = debounce(callback, 200);
    super(callback);
  }
}


</script>
